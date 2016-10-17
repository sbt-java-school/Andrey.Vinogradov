package ru.sbt.home.task16.strategy.tointeger;

import ru.sbt.home.task16.strategy.AbstractStrategy;

import java.math.BigDecimal;

public class BigDecimalToInteger extends AbstractStrategy<BigDecimal, Integer> {
	public BigDecimalToInteger(Class<BigDecimal> in, Class<Integer> out) {
		super(in, out);
	}
	
	@Override
	public Integer convert(Object value) {
		return ((BigDecimal) value).intValue();
	}
}