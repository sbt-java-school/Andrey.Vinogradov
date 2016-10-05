package ru.sbt.home.task11;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Комната.
 * Просто контейнер. Не может выполнять действий.
 * Клиенты и парикмахеры самостоятельно перемещаются между комнатами.
 */
public class Room<T extends Runnable> {
	private ConcurrentLinkedQueue<T> container = new ConcurrentLinkedQueue<>();
	
	public void enter(T entity) {
		container.add(entity);
	}
	
	public void leave(T entity) {
		container.remove(entity);
	}
	
	public void forEach(Consumer<? super T> action) {
		container.forEach(action);
	}
	
	public Stream<T> stream() {
		return container.stream();
	}
}