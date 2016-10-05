package ru.sbt.home.task14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbt.home.task14.packets.Packet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AuthServer {
	public static final int PORT = 1234;
	public static final int DEFAULT_THREAD_COUNT = 10;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthServer.class);
	
	private ExecutorService authService;
	private ExecutorService serverService;
	
	private int threadCount = DEFAULT_THREAD_COUNT;
	private ServerSocket serverSocket;
	
	public AuthServer() {
		this(DEFAULT_THREAD_COUNT);
	}
	
	public AuthServer(int threadCount) {
		this.threadCount = threadCount;
	}
	
	public static void main(String[] args) throws InterruptedException {
		AuthServer server = new AuthServer();
		
		server.start();
		
		TimeUnit.SECONDS.sleep(60);
		
		server.stop();
	}
	
	public void start() {
		authService = Executors.newFixedThreadPool(threadCount);
		serverService = Executors.newSingleThreadExecutor();
		
		try {
			serverSocket = new ServerSocket(PORT);
			
			LOGGER.info("Сервер запущен: " + serverSocket);
			
			serverService.submit(new Runnable() {
				@Override
				public void run() {
					try {
						serverSocket = new ServerSocket(PORT);
						
						Socket socket = null;
						
						while (true) {
							socket = serverSocket.accept();
							
							LOGGER.debug("Новое соединение: " + socket);
							
							authService.submit(new Authenticator(socket));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
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
		
		authService.shutdown();
		serverService.shutdown();
		
		LOGGER.info("Сервер остановлен: " + serverSocket);
	}
	
	public static class Authenticator extends AbstractSocketHandler {
		public Authenticator(Socket socket) throws IOException {
			super(socket);
		}
		
		public String pickAppServer() {
			return null;
		}
		
		@Override
		public void run() {
			// сертификат пользователя
			Packet packet = receiveData();
			packet = packet.process(this);
			
			// сертификат сервера
			sendData(packet);
			
			// логин-пароль
			packet = receiveData();
			packet = packet.process(this);
			
			// ключ шифрования сервера
			sendData(packet);
			
			// ключ шифрования клиента
			packet = receiveData();
			packet = packet.process(this);
			
			// координаты сервера приложений
			sendData(packet);
		}
	}
}