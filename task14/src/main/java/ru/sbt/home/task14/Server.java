package ru.sbt.home.task14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbt.home.task14.messages.Message;
import ru.sbt.home.task14.messages.MessageImpl;
import ru.sbt.home.task14.packets.Packet;

import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Сервер
 * Занимается аутентификацией, мультикастом вошедших пользователей, пересылкой сообщений от пользователя к пользователю (это должен делать отдельный сервер приложений)
 */
public class Server {
	public static final int TCP_PORT = 9050;
	public static final int UDP_PORT = 9060;
	public static final int DEFAULT_THREAD_COUNT = 10;
	
	public static final String MULTI_HOST = "228.5.6.7";
	public static final int MULTI_PORT = 80;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
	
	private ExecutorService authService;
	private ExecutorService serverService;
	
	private ExecutorService chatService;
	
	private int threadCount = DEFAULT_THREAD_COUNT;
	private ServerSocket serverSocket;
	
	private Map<String, UDPSender> users = new ConcurrentHashMap<>();
	
	private UDPSender multiSender;
	
	public Server() throws SocketException, UnknownHostException {
		this(DEFAULT_THREAD_COUNT);
	}
	
	public Server(int threadCount) throws SocketException, UnknownHostException {
		this.threadCount = threadCount;
		
		multiSender = new UDPSender(InetAddress.getByName(MULTI_HOST), MULTI_PORT);
	}
	
	public static void main(String[] args) throws InterruptedException, SocketException, UnknownHostException {
		Server server = new Server();
		
		server.start();
	}
	
	public void start() {
		authService = Executors.newFixedThreadPool(threadCount);
		
		serverService = Executors.newSingleThreadExecutor();
		
		try {
			serverSocket = new ServerSocket(TCP_PORT);
			
			LOGGER.info("Сервер запущен: " + serverSocket);
			
			serverService.submit(() -> {
				try {
					while (true) {
						Socket socket = serverSocket.accept();
						
						LOGGER.debug("Новое соединение: " + socket);
						
						authService.submit(new Authenticator(socket));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		chatService = Executors.newSingleThreadExecutor();
		
		chatService.submit(() -> {
			try {
				UDPReceiver handler = new UDPReceiver(UDP_PORT);
				
				while (true) {
					Message msg = handler.receiveData();
					
					LOGGER.debug("Сообщение от " + msg.getSender());
					
					if (msg.getReceiver().isEmpty()) {
						continue;
					}
					
					UDPSender userHandler = users.get(msg.getReceiver());
					
					if (userHandler == null) {
						return;
					}
					
					userHandler.sendData(msg);
					
					LOGGER.debug("Сообщение отправлено " + msg.getReceiver());
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
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
	
	public void addUser(String login, InetAddress address) throws SocketException, UnknownHostException {
		users.put(login, new UDPSender(address, Client.UDP_PORT));
		
		multiSender.sendData(new MessageImpl(Message.SYSTEM, null, "Присоединился пользователь " + login));
	}
	
	/**
	 * Аутентификатор
	 * Запускается отдельно для каждого постучавшегося
	 */
	public class Authenticator extends TCPHandler {
		public Authenticator(Socket socket) throws IOException {
			super(socket);
		}
		
		@Override
		public void run() {
			LOGGER.debug("Начало аутентификации клиента");
			
			// логин-пароль
			Packet packet = receiveData();
			packet = packet.process(Server.this);
			
			// координаты сервера приложений
			sendData(packet);
			
			// координаты клиента
			packet = receiveData();
			packet.process(Server.this);
			
			LOGGER.debug("Окончание аутентификации клиента");
		}
	}
}