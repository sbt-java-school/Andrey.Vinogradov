package ru.sbt.home.task16.strategy;

/**
 * Основной интерфейс стратегии конвертации
 *
 * @param <IN>  тип входящего значения
 * @param <OUT> тип возвращаемого значения
 */
public interface Strategy<IN, OUT> {
	public OUT convert(Object value);
	
	public Class<IN> getInClass();
	
	public Class<OUT> getOutClass();
}