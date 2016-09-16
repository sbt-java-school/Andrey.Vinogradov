package ru.sbt.home.task05;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

public class SizeCacheHandlerTest {
	Method method;
	CacheHandler dch = new SizeCacheHandler(2);
	
	@Before
	public void setUp() throws NoSuchMethodException {
		method = Calculator.class.getMethod("diff", long.class, long.class);
		
		Object[] args = new Object[]{3, 2};
		dch.put(method, args, 1);
		
		args = new Object[]{2, 1};
		dch.put(method, args, 1);
		
		args = new Object[]{7, 2};
		dch.put(method, args, 5);
	}
	
	@Test
	public void test() throws NoSuchMethodException {
		Object[] args = new Object[]{3, 2};
		Assert.assertEquals(args[0] + " - " + args[1] + " = null", null, dch.get(method, args));
		
		args = new Object[]{2, 1};
		Assert.assertEquals(args[0] + " - " + args[1] + " = 1", 1, dch.get(method, args));
		
		args = new Object[]{7, 2};
		Assert.assertEquals(args[0] + " - " + args[1] + " = 5", 5, dch.get(method, args));
	}
}