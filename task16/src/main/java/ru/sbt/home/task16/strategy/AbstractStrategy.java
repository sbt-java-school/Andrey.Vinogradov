package ru.sbt.home.task16.strategy;

/**
 * Вспомогательный класс
 * Реализован возврат классов входящего и возвращаемого значений
 *
 * @param <IN>  тип входящего значения
 * @param <OUT> тип возвращаемого значения
 */
public abstract class AbstractStrategy<IN, OUT> implements Strategy<IN, OUT> {
	private Class<IN> in;
	private Class<OUT> out;
	
	public AbstractStrategy(Class<IN> in, Class<OUT> out) {
		this.in = in;
		this.out = out;
	}
	
	@Override
	public Class<IN> getInClass() {
		return in;
	}
	
	@Override
	public Class<OUT> getOutClass() {
		return out;
	}
}