package ru.sbt.home.task15;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class EqualsNodeTest {
	@Test
	public void test() {
		Map<String, Object> data = new HashMap<>();
		
		data.put("city", "Novosibirsk");
		data.put("salary", 190_000);
		data.put("credit term", 10);
		data.put("loan payment", 10_000);
		
		EqualsNode node = new EqualsNode();
		node.addNode(new ConstantNode("1"));
		node.addNode(new ConstantNode("1"));
		
		Assert.assertEquals("\"1\" = \"1\"", true, node.getResult(data));
		
		node = new EqualsNode();
		node.addNode(new ConstantNode(1));
		node.addNode(new ConstantNode("1"));
		
		Assert.assertEquals("1 = \"1\"", false, node.getResult(data));
	}
}