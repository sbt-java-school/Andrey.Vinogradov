package ru.sbt.home.task02;

import org.junit.Assert;
import org.junit.Test;

public class TruckDaoTest {
	@Test
	public void test() {
		TruckDao td = new TruckDaoMemoryImpl();
		
		Assert.assertEquals("Count = 6", 6, td.list().size());
		Assert.assertEquals("Count = 6", 6, td.listByID().size());
		Assert.assertEquals("Count = 3", 3, td.listByType().size());
	}
}