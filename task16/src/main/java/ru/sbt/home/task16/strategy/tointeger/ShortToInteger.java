package ru.sbt.home.task16.strategy.tointeger;

import ru.sbt.home.task16.strategy.AbstractStrategy;

public class ShortToInteger extends AbstractStrategy<Short, Integer> {
	public ShortToInteger(Class<Short> in, Class<Integer> out) {
		super(in, out);
	}
	
	@Override
	public Integer convert(Object value) {
		return ((Short) value).intValue();
	}
}