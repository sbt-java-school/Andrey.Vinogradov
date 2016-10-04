package ru.sbt.home.task13.packets;

import ru.sbt.home.task13.Server;

/**
 * Пакет "Попробуй еще", обрабатывается на сервере, содержит сравнение загаданного числа и последнего предположения, породившего этот пакет:
 * -1 загаданное число меньше
 * 0 угадал
 * 1 загаданное число больше
 */
public class TryAgain implements Packet<Integer, Server.SocketHandler> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Результат сравнения
	 */
	private Integer compareResult;
	
	public TryAgain(int comparisonResult) {
		this.compareResult = comparisonResult;
	}
	
	@Override
	public Integer getQuery() {
		return compareResult;
	}
	
	/**
	 * Обработка ответа от клиента.
	 * Если результат сравнения 0 - останавливает процесс сервера
	 * Иначе делает следующее предположение
	 *
	 * @param context процесс сервера
	 * @return пакет Guess с новым предположением
	 */
	@Override
	public Packet process(Server.SocketHandler context) {
		// остановка сервера, если угадали в прошлый раз
		if (compareResult == 0) {
			context.stopGuess();
			
			return null;
		}
		
		// новое предположение на основе сравнения
		return new Guess(context.makeNextGuess(compareResult));
	}
}