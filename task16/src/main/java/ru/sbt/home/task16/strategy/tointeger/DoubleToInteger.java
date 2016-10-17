package ru.sbt.home.task16.strategy.tointeger;

import ru.sbt.home.task16.strategy.AbstractStrategy;

public class DoubleToInteger extends AbstractStrategy<Double, Integer> {
	public DoubleToInteger(Class<Double> in, Class<Integer> out) {
		super(in, out);
	}
	
	@Override
	public Integer convert(Object value) {
		return ((Double) value).intValue();
	}
}