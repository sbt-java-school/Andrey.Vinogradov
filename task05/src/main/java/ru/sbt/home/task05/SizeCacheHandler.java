package ru.sbt.home.task05;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class SizeCacheHandler implements CacheHandler {
	/**
	 * Хэш мапа для хранения кэша.
	 * Реализовано через LinkedHashMap с accessOrder = true - т.е. при обращении через get найденная Entry смещается в конец двунаправленного списка.
	 * Начало списка - самые старые Entry (по добавлению или чтению)
	 * Конец списка - самые новые Entry (по добавлению или чтению)
	 * Поскольку синхронизирован только сам кэш, может возникнуть ситуация, когда 2 конкурирующих потока для одной сигнатуры сначала ищут ее в кэше и не находят, а потом обав ставляют.
	 * При этом при втором помещении сигнатуры не происходит ее смещение в конец списка (не становится самой новой).
	 * Но поскольку такое произойдет только при первом помещении и предыдущее помещение будет прямо перед вторым - особой проблемы не будет
	 */
	private final Map<Signature, Object> cache;
	
	/**
	 * Количество хранимых реультатов
	 */
	private int size;
	
	public SizeCacheHandler(long size) {
		this.size = size > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) size;
		
		cache = Collections.synchronizedMap(new LinkedHashMap<Signature, Object>(this.size, 1f, true)); // синхронизированная версия мапы
	}
	
	@Override
	public long getValue() {
		return size;
	}
	
	@Override
	public void setValue(long newSize) {
		size = newSize > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) newSize;
		
		if (cache.size() < size) {
			return;
		}
		
		for (Iterator<Map.Entry<Signature, Object>> i = cache.entrySet().iterator(); cache.size() > size && i.hasNext(); ) {
			i.next();
			i.remove();
		}
	}
	
	/**
	 * Поиск результата по сигнатуре вызова.
	 * Если найдено - сигнатура полученного результата перемещается в конец списка.
	 *
	 * @param method вызываемый метод
	 * @param args   параметры вызова
	 * @return результат из кэша, null - не найден
	 */
	@Override
	public Object get(Method method, Object[] args) {
		Signature key = new Signature(method, args);
		
		return cache.get(key);
	}
	
	/**
	 * Помещение результата в кэш по сигнатуре вызова.
	 * Сигнатура результата перемещается в конец списка.
	 *
	 * @param method вызываемый метод
	 * @param args   параметры вызова
	 * @param value  результат вызова для помещения в кэш
	 */
	@Override
	public void put(Method method, Object[] args, Object value) {
		Signature key = new Signature(method, args);
		
		cache.put(key, value);
		
		// для удаления старых элементов можно отнаследовать LinkedHashMap и переписать метод removeEldestEntry
		// но можно и так, ручками
		for (Iterator<Map.Entry<Signature, Object>> i = cache.entrySet().iterator(); cache.size() > size && i.hasNext(); ) {
			i.next();
			i.remove();
		}
	}
}