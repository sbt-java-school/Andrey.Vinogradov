package ru.sbt.home.task15;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ParameterNodeTest {
	@Test
	public void test() {
		Map<String, Object> data = new HashMap<>();
		
		data.put("city", "Novosibirsk");
		data.put("salary", 190_000d);
		data.put("credit term", 10);
		data.put("loan payment", 10_000f);
		
		ParameterNode<String> node1 = new ParameterNode<>("city");
		Assert.assertEquals("city = Novosibirsk", "Novosibirsk", node1.getResult(data));
		
		ParameterNode<Double> node2 = new ParameterNode<>("salary");
		Assert.assertEquals("salary = 190_000", Double.valueOf(190_000d), node2.getResult(data));
		
		ParameterNode<Integer> node3 = new ParameterNode<>("credit term");
		Assert.assertEquals("credit term = 10", Integer.valueOf(10), node3.getResult(data));
		
		ParameterNode<Float> node4 = new ParameterNode<>("loan payment");
		Assert.assertEquals("loan payment = 10_000", Float.valueOf(10_000f), node4.getResult(data));
	}
}