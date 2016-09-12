package ru.sbt.home.task01;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TruckTest {
	@Test
	public void test() throws NoSuchMethodException {
		Truck truck = new Truck(10);
		
		Arrays.asList(5, 10, 1, 3, 7, 1, 2).forEach(truck::load);
		
		Assert.assertEquals("Count = 4", 4, truck.getCount());
		Assert.assertEquals("Mass = 10", 10, truck.getMass());
	}
}