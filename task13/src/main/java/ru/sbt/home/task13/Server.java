package ru.sbt.home.task13;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbt.home.task13.packets.Guess;
import ru.sbt.home.task13.packets.Packet;
import ru.sbt.home.task13.packets.ThinkOf;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	public static final int PORT = 1234;
	public static final int DEFAULT_THREAD_COUNT = 10;
	public static final int DEFAULT_PUZZLE_BOUND = 10;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
	
	private int threadCount = DEFAULT_THREAD_COUNT;
	private int puzzleBound = DEFAULT_PUZZLE_BOUND;
	
	private ExecutorService clientService;
	private ExecutorService serverService;
	
	private ServerSocket serverSocket;
	
	public Server() {
		this(DEFAULT_THREAD_COUNT, DEFAULT_PUZZLE_BOUND);
	}
	
	public Server(int threadCount, int puzzleBound) {
		this.threadCount = threadCount;
		this.puzzleBound = puzzleBound;
	}
	
	public void start() {
		clientService = Executors.newFixedThreadPool(threadCount);
		serverService = Executors.newSingleThreadExecutor();
		
		try {
			serverSocket = new ServerSocket(PORT);
			
			LOGGER.info("Сервер запущен: " + serverSocket);
			
			serverService.submit(() -> {
				try {
					Socket socket = null;
					
					while (true) {
						socket = serverSocket.accept();
						
						System.out.println("Новое соединение: " + socket);
						
						clientService.submit(new SocketHandler(socket, puzzleBound));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		clientService.shutdown();
		serverService.shutdown();
		
		LOGGER.info("Сервер остановлен: " + serverSocket);
	}
	
	public class SocketHandler extends AbstractSocketHandler {
		private int lastGuess;
		private int lastShift;
		private int tryCount;
		
		private boolean keepGoing;
		
		public SocketHandler(Socket socket, int puzzleBound) throws IOException {
			super(socket);
			
			lastGuess = puzzleBound;
			lastShift = puzzleBound;
			
			tryCount = 0;
			
			keepGoing = true;
			
			LOGGER.debug("Новый обработчик: " + Thread.currentThread().getName());
		}
		
		/**
		 * Новое предположение на основе сравнения от клиента, вызывается из пакета
		 *
		 * @param comparisonResult результат сравнения:
		 *                         -1 если загадано меньше
		 *                         0 если отгадано
		 *                         1 если загадано больше
		 * @return новое предположение
		 */
		protected int makeNextGuess(int comparisonResult) {
			// последний сдвиг, используемый в предположении
			// +1 - чтобы не уйти в 0 (всегда будет не меньше 1)
			lastShift = (lastShift + 1) / 2;
			
			// новое предположение
			lastGuess += comparisonResult * lastShift;
			
			// количество попыток
			tryCount++;
			
			return lastGuess;
		}
		
		/**
		 * Остновка прцесса сервера, вызывается из пакета
		 */
		protected void stopGuess() {
			keepGoing = false;
		}
		
		/**
		 * Основной метод
		 */
		@Override
		public void run() {
			LOGGER.debug(Thread.currentThread().getName() + ": число 0.." + (puzzleBound - 1));
			
			// передача верхней границы для загадывания
			Packet packet = new ThinkOf(puzzleBound);
			sendData(packet);
			
			LOGGER.debug(Thread.currentThread().getName() + ": " + lastGuess + "?");
			
			// первое предположение
			packet = new Guess(makeNextGuess(-1));
			sendData(packet);
			
			// цикл создания новых предположений
			while (keepGoing && (packet = receiveData()) != null) {
				packet = packet.process(this);
				
				// отвечаем, если есть что
				if (packet != null) {
					LOGGER.debug(Thread.currentThread().getName() + ": " + lastGuess + "?");
					
					sendData(packet);
				}
			}
			
			LOGGER.debug(Thread.currentThread().getName() + ": отгадано = " + lastGuess + ", попыток = " + tryCount);
		}
	}
}