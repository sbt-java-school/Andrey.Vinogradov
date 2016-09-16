package ru.sbt.home.task05;

import java.lang.reflect.Method;

enum CacheType implements CacheHandler {
	DEFAULT(new DefaultCacheHandler()), TIME(new TimeCacheHandler(60L)), SIZE(new SizeCacheHandler(3L));
	
	/**
	 * Основной обработчик кэша
	 */
	private CacheHandler ch;
	
	/**
	 * Конструктор с обязательным указанием основного обработчика
	 *
	 * @param ch основной обработчик
	 */
	CacheType(CacheHandler ch) {
		this.ch = ch;
	}
	
	public long getValue() {
		return ch.getValue();
	}
	
	public void setValue(long value) {
		ch.setValue(value);
	}
	
	/**
	 * Получение кэшированного результата по сигнатуре
	 *
	 * @param method вызываемый метод
	 * @param args   параметры вызова
	 * @return
	 */
	public Object get(Method method, Object[] args) {
		return ch.get(method, args);
	}
	
	/**
	 * Помещение значения в кэш по сигнатуре
	 *
	 * @param method вызываемый метод
	 * @param args   параметры вызова
	 * @param value  результат вызова для помещения в кэш
	 */
	public void put(Method method, Object[] args, Object value) {
		ch.put(method, args, value);
	}
}