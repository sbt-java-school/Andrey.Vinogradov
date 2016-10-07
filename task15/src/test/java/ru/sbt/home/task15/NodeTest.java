package ru.sbt.home.task15;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class NodeTest {
	@Test
	public void test() {
		Map<String, Object> data = new HashMap<>();
		
		data.put("city", "Novosibirsk");
		data.put("salary", 190_000d);
		data.put("credit term", 10);
		data.put("loan payment", 10_000f);
		
		Node root = new EqualsNode();
		
		Node l1 = new EqualsNode();
		
		root.addNode(l1);
		
		l1.addNode(new ParameterNode<String>("city"));
		l1.addNode(new ConstantNode<String>("Novosibirsk"));
		
		Node r1 = new CompareNode<Double>(CompareNode.Operation.LARGER_OR_EQUAL);
		root.addNode(r1);
		
		Node r2 = r1.addNode(new DivNode());
		r2.addNode(new ParameterNode<Number>("salary"));
		r2.addNode(new ConstantNode<Double>(2D));
		
		r2 = r1.addNode(new MultNode());
		r2.addNode(new ParameterNode<Number>("credit term"));
		r2.addNode(new ParameterNode<Number>("loan payment"));
		
		Assert.assertEquals("city = Novosibirsk & salary / 2 >= credit term * loan payment", false, root.getResult(data));
	}
}