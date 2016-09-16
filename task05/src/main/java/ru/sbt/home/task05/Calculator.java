package ru.sbt.home.task05;

import java.math.BigInteger;

/**
 * Интерфейс калькулятор для тестов
 */
interface Calculator {
	@Cache
	BigInteger sum(long i, long j);
	
	@Cache(CacheType.SIZE)
	BigInteger diff(long i, long j);
	
	@Cache(CacheType.TIME)
	BigInteger mult(long i, long j);
	
	@Cache(CacheType.TIME)
	BigInteger div(long i, long j);
}