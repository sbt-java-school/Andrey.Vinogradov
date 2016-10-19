package ru.sbt.home.task16.strategy.tointeger;

import ru.sbt.home.task16.strategy.AbstractStrategy;

public class StringToInteger extends AbstractStrategy<String, Integer> {
	public StringToInteger(Class<String> in, Class<Integer> out) {
		super(in, out);
	}
	
	@Override
	public Integer convert(Object value) {
		return Double.valueOf((String) value).intValue();
	}
}