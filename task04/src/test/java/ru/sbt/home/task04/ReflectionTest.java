package ru.sbt.home.task04;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ReflectionTest {
	@Test
	public void test() {
		Arrays.asList(Test.class.getAnnotations()).forEach(System.out::println);
		Arrays.asList(Test.class.getFields()).forEach(System.out::println);
		Arrays.asList(Test.class.getConstructors()).forEach(System.out::println);
		Arrays.asList(Test.class.getMethods()).forEach(System.out::println);
		
		Assert.assertEquals("Annotations = 2", 2, Test.class.getAnnotations().length);
		Assert.assertEquals("Fields = 0", 0, Test.class.getFields().length);
		Assert.assertEquals("Constructors = 0", 0, Test.class.getConstructors().length);
		Assert.assertEquals("Methods = 6", 6, Test.class.getMethods().length);
	}
}