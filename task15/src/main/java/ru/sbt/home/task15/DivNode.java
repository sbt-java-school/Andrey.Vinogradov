package ru.sbt.home.task15;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Нод-деление. Содержит только 2 субнода, делит первый (left) на второй (right)
 * Возвращает Double, субноды возвращают Number
 */
public class DivNode implements Node<Double, Number> {
	private Node<Number, ?> left;
	private Node<Number, ?> right;
	
	@Override
	public Double getResult(Map<String, Object> data) {
		double res = 0;
		
		if (left != null) {
			res = (left.getResult(data)).doubleValue();
		}
		
		if (right != null) {
			res /= (right.getResult(data)).doubleValue();
		}
		
		return res;
	}
	
	@Override
	public Node<Number, ?> addNode(Node<Number, ?> node) {
		if (left == null) {
			left = node;
		} else if (right == null) {
			right = node;
		}
		
		return node;
	}
	
	@Override
	public Node<Number, ?> removeNode(Node<Number, ?> node) {
		if (right != null) {
			right = null;
		} else if (left != null) {
			left = null;
		}
		
		return node;
	}
	
	@Override
	public List<Node<Number, ?>> nodeList() {
		List<Node<Number, ?>> res = new LinkedList<>();
		if (left != null) {
			res.add(left);
			
			if (right != null) {
				res.add(right);
			}
		}
		
		return res;
	}
}