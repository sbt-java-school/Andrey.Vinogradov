package ru.sbt.home.task15;

import java.util.List;
import java.util.Map;

/**
 * Терминальный нод-константа. Просто возвращает заданное в конструкторе значение
 *
 * @param <T> тип возвращаемого параметра
 */
public class ConstantNode<T> implements Node<T, Object> {
	private T value;
	
	public ConstantNode(T value) {
		this.value = value;
	}
	
	@Override
	public T getResult(Map<String, Object> data) {
		return value;
	}
	
	@Override
	public Node<Object, ?> addNode(Node<Object, ?> node) {
		return null;
	}
	
	@Override
	public Node<Object, ?> removeNode(Node<Object, ?> node) {
		return null;
	}
	
	@Override
	public List<Node<Object, ?>> nodeList() {
		return null;
	}
}