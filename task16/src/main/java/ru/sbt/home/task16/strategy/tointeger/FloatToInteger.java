package ru.sbt.home.task16.strategy.tointeger;

import ru.sbt.home.task16.strategy.AbstractStrategy;

public class FloatToInteger extends AbstractStrategy<Float, Integer> {
	public FloatToInteger(Class<Float> in, Class<Integer> out) {
		super(in, out);
	}
	
	@Override
	public Integer convert(Object value) {
		return ((Float) value).intValue();
	}
}