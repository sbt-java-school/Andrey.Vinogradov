package ru.sbt.home.task15;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DivNodeTest {
	@Test
	public void test() {
		Map<String, Object> data = new HashMap<>();
		
		data.put("city", "Novosibirsk");
		data.put("salary", 190_000);
		data.put("credit term", 10);
		data.put("loan payment", 10_000);
		
		DivNode node = new DivNode();
		node.addNode(new ConstantNode(2));
		node.addNode(new ConstantNode(3));
		node.addNode(new ConstantNode(1));
		
		Assert.assertEquals("2 / 3 = 0.6666666666666666", Double.valueOf(2d / 3d), node.getResult(data));
	}
}