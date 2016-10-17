package ru.sbt.home.task14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbt.home.task14.messages.Message;
import ru.sbt.home.task14.messages.MessageImpl;
import ru.sbt.home.task14.net.TCPHandler;
import ru.sbt.home.task14.net.UDPReceiver;
import ru.sbt.home.task14.net.UDPSender;
import ru.sbt.home.task14.packets.Packet;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Сервер
 * Занимается аутентификацией, мультикастом вошедших пользователей, пересылкой сообщений от пользователя к пользователю (это должен делать отдельный сервер приложений)
 */
public class Server {
	public static final int TCP_PORT = 9050;
	public static final int UDP_PORT = 9060;
	public static final int DEFAULT_THREAD_COUNT = 10;

	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

	private ExecutorService authService;
	private ExecutorService serverService;

	private ExecutorService chatService;

	private int threadCount = DEFAULT_THREAD_COUNT;

	private Map<String, UDPSender> users = new ConcurrentHashMap<>();

	private List<Closeable> closeList = new LinkedList<>();

	private List<ServerListener> listeners = new LinkedList<>();

	public Server() throws SocketException, UnknownHostException {
		this(DEFAULT_THREAD_COUNT);
	}

	public Server(int threadCount) throws SocketException, UnknownHostException {
		this.threadCount = threadCount;

		addServerListener(new ClientActivityListener());
	}

	public void addServerListener(ServerListener listener) {
		listeners.add(listener);
	}

	public void removeServerListener(ServerListener listener) {
		listeners.remove(listener);
	}

	public static void main(String[] args) throws InterruptedException, SocketException, UnknownHostException {
		Server server = new Server();

		server.start();

/*
		TimeUnit.SECONDS.sleep(5);

		server.stop();
*/
	}

	public void start() {
		authService = Executors.newFixedThreadPool(threadCount);

		serverService = Executors.newSingleThreadExecutor();

		try {
			ServerSocket serverSocket = new ServerSocket(TCP_PORT);
			closeList.add(serverSocket);

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
				closeList.add(handler);

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
		for (Closeable c : closeList) {
			try {
				c.close();
			} catch (IOException e) {
				// ignore
			}
		}

		authService.shutdown();
		serverService.shutdown();
		chatService.shutdown();

		LOGGER.info("Сервер остановлен");
	}

	public void addUser(String login, InetAddress address) throws SocketException, UnknownHostException {
		users.put(login, new UDPSender(address, Client.UDP_PORT));

		for (ServerListener tmp : listeners) {
			tmp.onClientConnect(login);
		}
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