package ru.sbt.home.task15;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EqualsNode implements Node<Boolean, Object> {
	private Node<Object, ?> left;
	private Node<Object, ?> right;
	
	@Override
	public Boolean getResult(Map<String, Object> data) {
		if (left == null || right == null) {
			return false;
		}
		
		Object leftResult = left.getResult(data);
		Object rightResult = right.getResult(data);
		
		if (leftResult == null || rightResult == null) {
			return false;
		}
		
		return leftResult.equals(rightResult);
	}
	
	@Override
	public Node<Object, ?> addNode(Node<Object, ?> node) {
		if (left == null) {
			left = node;
		} else if (right == null) {
			right = node;
		}
		
		return node;
	}
	
	@Override
	public Node<Object, ?> removeNode(Node<Object, ?> node) {
		if (right != null) {
			right = null;
		} else if (left != null) {
			left = null;
		}
		
		return node;
	}
	
	@Override
	public List<Node<Object, ?>> nodeList() {
		List<Node<Object, ?>> res = new LinkedList<>();
		if (left != null) {
			res.add(left);
			
			if (right != null) {
				res.add(right);
			}
		}
		
		return res;
	}
}