package ru.sbt.home.task15;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Нод-умножение. Перемножает возвращаемые всеми субнодами значения
 * Возвращает Double, субноды возвращают Number
 */
public class MultNode implements Node<Double, Number> {
	private List<Node<Number, ?>> childList = new LinkedList<>();
	
	@Override
	public Double getResult(Map<String, Object> data) {
		double res = 1;
		
		for (Node<Number, ?> node : childList) {
			res *= (node.getResult(data)).doubleValue();
		}
		
		return res;
	}
	
	@Override
	public Node<Number, ?> addNode(Node<Number, ?> node) {
		childList.add(node);
		
		return node;
	}
	
	@Override
	public Node<Number, ?> removeNode(Node<Number, ?> node) {
		childList.remove(node);
		
		return node;
	}
	
	@Override
	public List<Node<Number, ?>> nodeList() {
		return childList;
	}
}