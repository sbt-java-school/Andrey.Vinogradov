package ru.sbt.home.task05;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.lang.reflect.Method;
import java.util.Arrays;

public class DefaultCacheHandlerTest {
	@Parameter
	public long i;
	@Parameter(1)
	public long j;
	@Parameter(2)
	public long s;
	Method method;
	CacheHandler dch = new DefaultCacheHandler();
	
	@Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][]{{3, 2, 5}, {2, 1, 3}, {7, 2, 9}});
	}
	
	@Before
	public void setUp() throws NoSuchMethodException {
		method = Calculator.class.getMethod("sum", long.class, long.class);
	}
	
	@Test
	public void test() throws NoSuchMethodException {
		Object[] args = new Object[]{i, j};
		dch.put(method, args, s);
		
		Assert.assertEquals(i + " + " + j + " = " + s, s, dch.get(method, args));
	}
}