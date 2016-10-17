package ru.sbt.home.task16.strategy.tointeger;

import ru.sbt.home.task16.strategy.AbstractStrategy;

public class ByteToInteger extends AbstractStrategy<Byte, Integer> {
	public ByteToInteger(Class<Byte> in, Class<Integer> out) {
		super(in, out);
	}
	
	@Override
	public Integer convert(Object value) {
		return ((Byte) value).intValue();
	}
}