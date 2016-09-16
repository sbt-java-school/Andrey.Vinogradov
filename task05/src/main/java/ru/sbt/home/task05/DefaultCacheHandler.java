package ru.sbt.home.task05;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Кэш по умолчанию
 */
public class DefaultCacheHandler implements CacheHandler {
	/**
	 * Хэш мапа для хранения кэша
	 */
	private Map<Signature, Object> cache = new ConcurrentHashMap();
	
	public DefaultCacheHandler() {
	}
	
	@Override
	public long getValue() {
		return 0L;
	}
	
	@Override
	public void setValue(long value) {
	}
	
	/**
	 * Поиск результата по сигнатуре вызова
	 *
	 * @param method вызываемый метод
	 * @param args   параметры вызова
	 * @return сохраненный в кэше результат, null - не найден
	 */
	@Override
	public Object get(Method method, Object[] args) {
		Signature key = new Signature(method, args);
		
		return cache.get(key);
	}
	
	/**
	 * Помещение результата в кэш по сигнатуре вызова
	 *
	 * @param method вызываемый метод
	 * @param args   параметры вызова
	 * @param value  результат вызова для помещения в кэш
	 */
	@Override
	public void put(Method method, Object[] args, Object value) {
		Signature key = new Signature(method, args);
		
		cache.put(key, value);
	}
}