package ru.sbt.home.task15;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Нод сравнения. Отношение задается в конструкторе через энум Operation
 *
 * @param <T> тип возвращаемых значений субнодами
 */
public class CompareNode<T extends Comparable<T>> implements Node<Boolean, T> {
	private Node<T, ?> left;
	private Node<T, ?> right;
	
	private Operation operation;
	
	public CompareNode(Operation operation) {
		this.operation = operation;
	}
	
	@Override
	public Boolean getResult(Map<String, Object> data) {
		if (left == null || right == null) {
			return false;
		}
		
		T leftResult = (left.getResult(data));
		T rightResult = (right.getResult(data));
		
		if (leftResult == null || rightResult == null) {
			return false;
		}
		
		int compareRes = leftResult.compareTo(rightResult);
		
		boolean res = false;
		
		switch (operation) {
			case LESS:
				res = (compareRes < 0);
				break;
			case LESS_OR_EQUAL:
				res = (compareRes <= 0);
				break;
			case LARGER:
				res = (compareRes > 0);
				break;
			case LARGER_OR_EQUAL:
				res = (compareRes >= 0);
				break;
			case EQUAL:
				res = (compareRes == 0);
				break;
		}
		
		return res;
	}
	
	@Override
	public Node<T, ?> addNode(Node<T, ?> node) {
		if (left == null) {
			left = node;
		} else if (right == null) {
			right = node;
		}
		
		return node;
	}
	
	@Override
	public Node<T, ?> removeNode(Node<T, ?> node) {
		if (right != null) {
			right = null;
		} else if (left != null) {
			left = null;
		}
		
		return node;
	}
	
	@Override
	public List<Node<T, ?>> nodeList() {
		List<Node<T, ?>> res = new LinkedList<>();
		if (left != null) {
			res.add(left);
			
			if (right != null) {
				res.add(right);
			}
		}
		
		return res;
	}
	
	public enum Operation {
		LESS, LESS_OR_EQUAL, LARGER, LARGER_OR_EQUAL, EQUAL;
	}
}