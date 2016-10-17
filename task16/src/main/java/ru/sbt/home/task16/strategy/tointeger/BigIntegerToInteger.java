package ru.sbt.home.task16.strategy.tointeger;

import ru.sbt.home.task16.strategy.AbstractStrategy;

import java.math.BigInteger;

public class BigIntegerToInteger extends AbstractStrategy<BigInteger, Integer> {
	public BigIntegerToInteger(Class<BigInteger> in, Class<Integer> out) {
		super(in, out);
	}
	
	@Override
	public Integer convert(Object value) {
		return ((BigInteger) value).intValue();
	}
}