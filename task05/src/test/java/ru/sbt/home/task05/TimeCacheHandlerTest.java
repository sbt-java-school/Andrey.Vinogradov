package ru.sbt.home.task05;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

public class TimeCacheHandlerTest {
	Method method;
	CacheHandler dch = new TimeCacheHandler(10);
	
	@Before
	public void setUpClass() throws NoSuchMethodException {
		method = Calculator.class.getMethod("mult", long.class, long.class);
		
		Object[] args = new Object[]{3, 2};
		dch.put(method, args, 6);
		
		args = new Object[]{2, 1};
		dch.put(method, args, 2);
		
		args = new Object[]{7, 2};
		dch.put(method, args, 14);
	}
	
	@Test
	public void test() throws NoSuchMethodException, InterruptedException {
		Object[] args = new Object[]{3, 2};
		Assert.assertEquals(args[0] + " * " + args[1] + " = 6", 6, dch.get(method, args));
		
		args = new Object[]{2, 1};
		Assert.assertEquals(args[0] + " * " + args[1] + " = 2", 2, dch.get(method, args));
		
		Thread.sleep(15_000);
		
		args = new Object[]{7, 2};
		Assert.assertEquals(args[0] + " * " + args[1] + " = null", null, dch.get(method, args));
	}
}