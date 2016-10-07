package ru.sbt.home.task15;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MultNodeTest {
	@Test
	public void test() {
		Map<String, Object> data = new HashMap<>();
		
		data.put("city", "Novosibirsk");
		data.put("salary", 190_000);
		data.put("credit term", 10);
		data.put("loan payment", 10_000);
		
		MultNode node = new MultNode();
		node.addNode(new ConstantNode<>(1));
		node.addNode(new ConstantNode<>(2));
		node.addNode(new ConstantNode<>(3));
		
		Assert.assertEquals("1 * 2 * 3 = 6", Double.valueOf(6), node.getResult(data));
	}
}