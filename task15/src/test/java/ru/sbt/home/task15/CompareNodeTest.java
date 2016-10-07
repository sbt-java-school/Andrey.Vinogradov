package ru.sbt.home.task15;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CompareNodeTest {
	@Test
	public void test() {
		Map<String, Object> data = new HashMap<>();
		
		data.put("city", "Novosibirsk");
		data.put("salary", 190_000);
		data.put("credit term", 10);
		data.put("loan payment", 10_000);
		
		CompareNode<Double> node = new CompareNode<>(CompareNode.Operation.LARGER);
		node.addNode(new ConstantNode<>(2D));
		node.addNode(new ConstantNode<>(3D));
		
		Assert.assertEquals("2 > 3", false, node.getResult(data));
		
		node = new CompareNode<>(CompareNode.Operation.LARGER_OR_EQUAL);
		node.addNode(new ConstantNode<>(2D));
		node.addNode(new ConstantNode<>(3D));
		
		Assert.assertEquals("2 >= 3", false, node.getResult(data));
		
		node = new CompareNode<>(CompareNode.Operation.EQUAL);
		node.addNode(new ConstantNode<>(2D));
		node.addNode(new ConstantNode<>(3D));
		
		Assert.assertEquals("2 == 3", false, node.getResult(data));
		
		node = new CompareNode<>(CompareNode.Operation.LESS);
		node.addNode(new ConstantNode<>(2D));
		node.addNode(new ConstantNode<>(3D));
		
		Assert.assertEquals("2 < 3", true, node.getResult(data));
		
		node = new CompareNode<>(CompareNode.Operation.LESS_OR_EQUAL);
		node.addNode(new ConstantNode<>(2D));
		node.addNode(new ConstantNode<>(3D));
		
		Assert.assertEquals("2 <= 3", true, node.getResult(data));
	}
}