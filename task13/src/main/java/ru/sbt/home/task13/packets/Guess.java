package ru.sbt.home.task13.packets;

import ru.sbt.home.task13.Client;

/**
 * Пакет "Предположение", обрабатывается на клиенте, содержит предположение сервера, порождает ответный пакет TryAgain
 */
public class Guess implements Packet<Integer, Client> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Предположение сервера
	 */
	private Integer offer;
	
	public Guess(int offer) {
		this.offer = offer;
	}
	
	@Override
	public Integer getQuery() {
		return offer;
	}
	
	/**
	 * Проверяет предположение сервера, порождает ответный пакет TryAgain с подсказкой сравнения загаданного и предполагаемого числа.
	 * Если предположение правильное - останавливает клиента.
	 *
	 * @param context клиент
	 * @return пакет TryAgain с результатом сравнения
	 */
	@Override
	public Packet process(Client context) {
		// сравнение загаданного числа и предположения
		int reply = context.compareToPuzzle(offer);
		
		// остановка клиента, если число угадано
		if (reply == 0) {
			context.stop();
		}
		
		// отечаем в любом случае
		return new TryAgain(reply);
	}
}