package ru.sbt.home.task05;

import java.lang.reflect.Method;
import java.util.*;

public class TimeCacheHandler implements CacheHandler {
	/**
	 * Хэш мапа для хранения кэша.
	 * Реализовано через LinkedHashMap с accessOrder = true - т.е. при обращении через get найденная Entry смещается в конец двунаправленного списка.
	 * Начало списка - самые старые Entry (по добавлению или чтению)
	 * Конец списка - самые новые Entry (по добавлению или чтению)
	 * Поскольку синхронизирован только сам кэш, может возникнуть ситуация, когда 2 конкурирующих потока для одной сигнатуры сначала ищут ее в кэше и не находят, а потом обав ставляют.
	 * При этом при втором помещении сигнатуры не происходит ее смещение в конец списка (не становится самой новой).
	 * Но поскольку такое произойдет только при первом помещении и предыдущее помещение будет прямо перед вторым - особой проблемы не будет
	 */
	private final Map<Signature, Node> cache;
	
	/**
	 * Время хранения в миллисекундах
	 */
	private long period;
	
	/**
	 * Демон для удаления старых сигнатур
	 */
	private Cleaner cleaner = new Cleaner();
	
	/**
	 * Конструктор, секунды из параметра переводятся в миллисекунды
	 *
	 * @param seconds время хранения в секундах
	 */
	public TimeCacheHandler(long seconds) {
		period = seconds * 1000;
		
		cache = Collections.synchronizedMap(new LinkedHashMap<Signature, Node>(16, 1f, true)); // синхронизированная версия мапы
	}
	
	@Override
	public long getValue() {
		return period;
	}
	
	/**
	 * Установка времени хранения, секунды из параметра переводятся в миллисекунды
	 *
	 * @param seconds время хранения в секундах
	 */
	@Override
	public void setValue(long seconds) {
		period = seconds * 1000;
		
		cleaner.schedule(0); // планирование демона очистки
	}
	
	/**
	 * Поиск результата по сигнатуре вызова.
	 * Если найдено - время вызова сигнатуры полученного результата меняется на текущее и сигнатура смещается в конец списка
	 *
	 * @param method вызываемый метод
	 * @param args   параметры вызова
	 * @return результат из кэша, null - не найден
	 */
	@Override
	public Object get(Method method, Object[] args) {
		Signature key = new Signature(method, args);
		
		Node tmp = cache.get(key);
		
		if (tmp != null) {
			tmp.millis = System.currentTimeMillis();
		}
		
		return tmp == null ? null : tmp.object;
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
		
		Node tmp = new Node(value, System.currentTimeMillis());
		
		cache.put(key, tmp);
		
		cleaner.schedule(period); // планирование демона очистки
	}
	
	class Node {
		protected Object object;
		protected long millis;
		
		public Node(Object object, long millis) {
			this.object = object;
			this.millis = millis;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == null || object == null) {
				return false;
			}
			if (this == o) {
				return true;
			}
			if (getClass() != o.getClass()) {
				return false;
			}
			
			Node tmp = (Node) o;
			
			return millis == tmp.millis && object.equals(tmp.object);
		}
		
		@Override
		public int hashCode() {
			int result = object != null ? object.hashCode() : 0;
			
			result = 31 * result + (int) millis;
			
			return result;
		}
	}
	
	class Cleaner extends TimerTask {
		/**
		 * Таймер для планирования демона очистки
		 */
		private Timer timer = new Timer(true);
		/**
		 * Время ближайшего вызова очистки
		 */
		private long nextCall = 0L;
		
		/**
		 * Планирование очистки.
		 * Если вызов запланирован на более раннее время - планирование не происходит.
		 * Если вызов запланирован на более позднее внемя - старое планирование отменяется, проходит новое планирование
		 *
		 * @param delay отсрочка запуска
		 */
		public void schedule(long delay) {
			long currentMillis = System.currentTimeMillis(); // текущее время
			
			if (currentMillis + delay > nextCall) { // если планируем на более позднее время - ничего не делаем
				return;
			}
			
			cancel(); // отменяем текущее
			
			nextCall = currentMillis + delay;
			
			timer.schedule(this, new Date(nextCall)); // новое планирование
		}
		
		/**
		 * Метод чистки кеша от старых сигнатур результатов
		 */
		@Override
		public void run() {
			for (Iterator<Map.Entry<Signature, Node>> i = cache.entrySet().iterator(); i.hasNext(); ) {
				long currentMillis = System.currentTimeMillis();
				
				Map.Entry<Signature, Node> tmp = i.next();
				
				if (tmp.getValue().millis + period < currentMillis) {
					i.remove();
				} else { // выходим как только попали на элемент с достаточно свежим временем
					nextCall = tmp.getValue().millis + period;
					
					timer.schedule(this, new Date(nextCall)); // новое планирование
					
					return;
				}
			}
		}
	}
}