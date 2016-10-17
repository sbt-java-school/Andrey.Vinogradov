package ru.sbt.home.task16.strategy.tointeger;

import ru.sbt.home.task16.strategy.AbstractStrategy;

public class LongToInteger extends AbstractStrategy<Long, Integer> {
	public LongToInteger(Class<Long> in, Class<Integer> out) {
		super(in, out);
	}
	
	@Override
	public Integer convert(Object value) {
		return ((Long) value).intValue();
	}
}