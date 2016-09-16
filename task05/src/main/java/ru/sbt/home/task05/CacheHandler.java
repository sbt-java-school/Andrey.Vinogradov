package ru.sbt.home.task05;

import java.lang.reflect.Method;
import java.util.Arrays;

public interface CacheHandler {
	/**
	 * Возвращает основную настройку кэша:
	 * <ul>
	 * <li>для кэша по умолчанию всегда 0L</li>
	 * <li>для кэша по времени - время хранения в секундах</li>
	 * <li>для кэша по размеру - количество хранимых результатов</li>
	 * </ul>
	 *
	 * @return основная настройка кэша
	 */
	long getValue();
	
	/**
	 * Установка основной настройки кэша:
	 * <ul>
	 * <li>для кэша по умолчанию всегда 0L</li>
	 * <li>для кэша по времени - время хранения в секундах</li>
	 * <li>для кэша по размеру - количество хранимых результатов</li>
	 * </ul>
	 *
	 * @param value основная настройка кэша
	 */
	void setValue(long value);
	
	/**
	 * Получает хранимый в кэше результат, null - не найден
	 *
	 * @param method вызываемый метод
	 * @param args   параметры вызова
	 * @return результат из кэша
	 */
	Object get(Method method, Object[] args);
	
	/**
	 * Помещает результат вызова в кэш
	 *
	 * @param method вызываемый метод
	 * @param args   параметры вызова
	 * @param value  результат вызова для помещения в кэш
	 */
	void put(Method method, Object[] args, Object value);
	
	/**
	 * Класс для хранения сигнатуры вызова - метод + параметры вызова
	 */
	class Signature {
		/**
		 * Метод сигнатуры
		 */
		protected Method method;
		/**
		 * Параметры вызова метода
		 */
		protected Object[] args;
		
		public Signature(Method method, Object[] args) {
			this.method = method;
			this.args = args;
		}
		
		/**
		 * Сравнение сигнатур. Для совпадения должен совпасть объект метода и массив параметров
		 *
		 * @param o объект для сравнения
		 * @return true - если сигнатуры совпадают
		 */
		@Override
		public boolean equals(Object o) {
			if (o == null || method == null) { // если сравниеваем с null или метод в текущей сигнатуре null
				return false;
			}
			if (this == o) { // сравнение по ссылке
				return true;
			}
			if (getClass() != o.getClass()) { // совпадение классов
				return false;
			}
			
			Signature tmp = (Signature) o; // каст
			
			return method.equals(tmp.method) && Arrays.equals(args, tmp.args); // совпадение метода и параметров вызова
		}
		
		@Override
		public int hashCode() {
			int result = method != null ? method.hashCode() : 0;
			
			result = 31 * result + Arrays.hashCode(args);
			
			return result;
		}
	}
}