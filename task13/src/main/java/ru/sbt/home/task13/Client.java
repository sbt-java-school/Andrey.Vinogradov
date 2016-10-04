package ru.sbt.home.task13;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbt.home.task13.packets.Packet;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends AbstractSocketHandler {
	public static final String HOST = "localhost";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
	
	private int puzzle;
	private boolean keepGoing;
	
	public Client() throws UnknownHostException, IOException {
		this(new Socket(HOST, Server.PORT));
	}
	
	public Client(Socket socket) throws IOException {
		super(socket);
		
		keepGoing = true;
		
		LOGGER.debug("Создан новый клиент " + socket);
	}
	
	/**
	 * Загадывание числа, вызывается из пакета
	 *
	 * @param numberBound
	 */
	protected void makePuzzle(int numberBound) {
		puzzle = (int) (numberBound * Math.random());
	}
	
	/**
	 * Сравнение с предположением сервера, вызыватся из пакета
	 *
	 * @param offer
	 * @return
	 */
	protected int compareToPuzzle(int offer) {
		return Integer.compare(puzzle, offer);
	}
	
	/**
	 * Остановка клиента, вызыватся из пакета
	 */
	protected void stop() {
		keepGoing = false;
	}
	
	/**
	 * Основной метод
	 */
	@Override
	public void run() {
		// Получение диапазона загадывания от сервера
		Packet packet = receiveData();
		packet.process(this);
		
		LOGGER.debug("Загадано: " + puzzle);
		
		// цикл ответов на предположения сервера
		while (keepGoing && (packet = receiveData()) != null) {
			packet = packet.process(this);
			
			// отвечаем, если есть что
			if (packet != null) {
				sendData(packet);
			}
		}
	}
}