package ru.sbt.home.task15;

import java.util.List;
import java.util.Map;

/**
 * Базовый интефейс
 *
 * @param <T> тип возвращемого значения
 * @param <V> тип возвращаемых значений субнодов
 */
public interface Node<T, V> {
	public T getResult(Map<String, Object> data);
	
	/**
	 * Добавление субнода
	 *
	 * @param node добавляемый субнод
	 * @return равен параметру
	 */
	public Node<? extends V, ?> addNode(Node<V, ?> node);
	
	/**
	 * Удление субнода
	 *
	 * @param node добавляемый субнод
	 * @return равен параметру
	 */
	public Node<? extends V, ?> removeNode(Node<V, ?> node);
	
	/**
	 * Субноды листом
	 *
	 * @return лист субнодов
	 */
	public List<Node<V, ?>> nodeList();
}