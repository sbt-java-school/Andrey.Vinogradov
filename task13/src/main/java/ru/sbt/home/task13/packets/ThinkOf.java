package ru.sbt.home.task13.packets;

import ru.sbt.home.task13.Client;

/**
 * Пакет "Задумай число", обрабатывается на клиенте, содержит верхнюю границу загадываемого числа (не включая)
 */
public class ThinkOf implements Packet<Integer, Client> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Верхняя граница загадываемого числа, не включая
	 */
	private Integer numberBound;
	
	public ThinkOf(int numberBound) {
		this.numberBound = numberBound;
	}
	
	@Override
	public Integer getQuery() {
		return numberBound;
	}
	
	/**
	 * Создает загаданное число и записывает его на клиенте
	 * @param context клиент
	 * @return всегда null
	 */
	@Override
	public Packet process(Client context) {
		// Запись загаданного числа на клиенте
		context.makePuzzle(numberBound);
		
		return null;
	}
}