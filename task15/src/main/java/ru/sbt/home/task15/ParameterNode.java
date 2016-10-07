package ru.sbt.home.task15;

import java.util.List;
import java.util.Map;

/**
 * Терминальный нод-параметр. Возвращет один из параметров, пареданным в мапе data
 * Ключ задается в конструкторе
 *
 * @param <T> тип возвращаемого параметра. Выкинет эксепшн, если параметр не приводится к нужному типу
 */
public class ParameterNode<T> implements Node<T, Object> {
	private String key;
	
	public ParameterNode(String key) {
		this.key = key;
	}
	
	@Override
	public T getResult(Map<String, Object> data) {
		if (key == null) {
			return null;
		}
		
		return (T) data.get(key);
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