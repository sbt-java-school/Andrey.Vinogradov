package ru.sbt.home.task16;

import org.junit.Assert;
import org.junit.Test;
import ru.sbt.home.task16.strategy.tointeger.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class ConverterTest {
	@Test
	public void test() {
		Converter converter = new ConverterImpl();
		
		converter.addStrategy(new BigDecimalToInteger(BigDecimal.class, Integer.class));
		Assert.assertTrue("BigDecimal 1.5 -> 1", converter.convert(BigDecimal.valueOf(1.5), Integer.class).equals(1));
		
		converter.addStrategy(new BigIntegerToInteger(BigInteger.class, Integer.class));
		Assert.assertTrue("BigInteger 1 -> 1", converter.convert(new BigInteger("1"), Integer.class).equals(1));
		
		converter.addStrategy(new ByteToInteger(Byte.class, Integer.class));
		Assert.assertTrue("Byte 1 -> 1", converter.convert((byte) 1, Integer.class).equals(1));
		
		converter.addStrategy(new DateToInteger(Date.class, Integer.class));
		Assert.assertTrue("Date 2 -> 1", converter.convert(new Date(2), Integer.class).equals(1));
		
		converter.addStrategy(new DoubleToInteger(Double.class, Integer.class));
		Assert.assertTrue("Double 1.5 -> 1", converter.convert(1.5, Integer.class).equals(1));
		
		converter.addStrategy(new FloatToInteger(Float.class, Integer.class));
		Assert.assertTrue("Float 1.5 -> 1", converter.convert(1.5F, Integer.class).equals(1));
		
		converter.addStrategy(new LongToInteger(Long.class, Integer.class));
		Assert.assertTrue("Long 1 -> 1", converter.convert(1L, Integer.class).equals(1));
		
		converter.addStrategy(new ShortToInteger(Short.class, Integer.class));
		Assert.assertTrue("Short 1 -> 1", converter.convert((short) 1, Integer.class).equals(1));
		
		converter.addStrategy(new StringToInteger(String.class, Integer.class));
		Assert.assertTrue("String 1 -> 1", converter.convert("1", Integer.class).equals(1));
	}
}