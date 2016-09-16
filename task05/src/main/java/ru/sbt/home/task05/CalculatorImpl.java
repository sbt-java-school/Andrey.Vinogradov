package ru.sbt.home.task05;

import java.math.BigInteger;

/**
 * Реализация калькулятора для тестов
 */
public class CalculatorImpl implements Calculator {
	@Override
	public BigInteger sum(long i, long j) {
		return BigInteger.valueOf(i + j);
	}
	
	@Override
	public BigInteger diff(long i, long j) {
		return BigInteger.valueOf(i - j);
	}
	
	@Override
	public BigInteger mult(long i, long j) {
		return BigInteger.valueOf(i * j);
	}
	
	@Override
	public BigInteger div(long i, long j) {
		return BigInteger.valueOf(i / j);
	}
}