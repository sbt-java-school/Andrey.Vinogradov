package ru.sbt.home.task04;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ReflectionTest {
	@Test
	public void test() {
		System.out.println("----- Annotations -----");
		Arrays.asList(Utility.class.getAnnotations()).forEach(System.out::println);
		System.out.println("----- Fields -----");
		Arrays.asList(FieldUtils.getAllFields(Utility.class)).forEach(System.out::println);
		System.out.println("----- Constructors -----");
		Arrays.asList(Utility.class.getConstructors()).forEach(System.out::println);
		System.out.println("----- Methods -----");
		Arrays.asList(Utility.class.getMethods()).forEach(System.out::println);
		
		Assert.assertEquals("Annotations = 0", 0, Utility.class.getAnnotations().length);
		Assert.assertEquals("Fields = 7", 7, FieldUtils.getAllFields(Utility.class).length);
		Assert.assertEquals("Constructors = 1", 1, Utility.class.getConstructors().length);
		Assert.assertEquals("Methods = 13", 13, Utility.class.getMethods().length);
		
		System.out.println("\n----- Inheritance tree -----");
		
		List<Class> parents = new LinkedList<>();
		
		for (Class tmp = Utility.class; tmp != null; tmp = tmp.getSuperclass()) {
			parents.add(0, tmp);
		}
		
		parents.forEach((Class clazz) -> System.out.println(clazz + "\n\t\u2193"));
	}
}