package ru.sbt.home.task14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbt.home.task14.messages.Message;
import ru.sbt.home.task14.messages.MessageImpl;
import ru.sbt.home.task14.net.TCPHandler;
import ru.sbt.home.task14.net.UDPReceiver;
import ru.sbt.home.task14.net.UDPSender;
import ru.sbt.home.task14.packets.Packet;
import ru.sbt.home.task14.packets.UserLogin;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Клиент
 * Занимается аутентификацией, рассылкой своих сообщений, приемом обычных сообщений и мультикастов
 */
public class Client {
	public static final int UDP_PORT = 19070;
	public static final String HOST = "localhost";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
	
	private String login;
	private String password;
	
	private InetAddress serverAddress;
	private UDPSender messenger;
	
	private ExecutorService service;
	
	private List<Closeable> closeList = new LinkedList<>();
	
	public Client(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Client client = new Client("логин", "пароль");
		
		client.start();
		client.authenticate();
		
		client.sendMessage(client.login, "Привет1");
		client.sendMessage(client.login, "Привет2");
		client.sendMessage(client.login, "Привет3");
		client.sendMessage(client.login, "Привет4");
		client.sendMessage(client.login, "Привет5");
		
		TimeUnit.SECONDS.sleep(5);
		
		client.stop();
	}
	
	public void start() throws SocketException, UnknownHostException {
		service = Executors.newFixedThreadPool(2);
		
		service.submit(() -> {
			try {
				UDPReceiver handler = new UDPReceiver(ClientActivityListener.MULTI_HOST, ClientActivityListener.MULTI_PORT);
				closeList.add(handler);
				
				while (true) {
					Message msg = handler.receiveData();
					
					LOGGER.debug(msg.getSender() + " >> " + msg.getMessage());
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		service.submit(() -> {
			try {
				UDPReceiver handler = new UDPReceiver(UDP_PORT);
				closeList.add(handler);
				
				while (true) {
					Message msg = handler.receiveData();
					
					LOGGER.debug(msg.getSender() + " >> " + msg.getMessage());
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		LOGGER.debug("Клиент запущен");
	}
	
	public void stop() {
		for (Closeable c : closeList) {
			try {
				c.close();
			} catch (IOException e) {
				// ignore
			}
		}
		service.shutdown();
		
		LOGGER.debug("Клиент остановлен");
	}
	
	public void authenticate() throws IOException {
		Socket tmp = new Socket(HOST, Server.TCP_PORT);
		closeList.add(tmp);
		
		new Authenticator(tmp).run();
		
		messenger = new UDPSender(serverAddress, Server.UDP_PORT);
	}
	
	public void sendMessage(String receiver, String msg) {
		messenger.sendData(new MessageImpl(login, receiver, msg));
	}
	
	public void setServerAddress(InetAddress address) {
		serverAddress = address;
	}
	
	public String getLogin() {
		return login;
	}
	
	/**
	 * Аутентификатор
	 * Объясняет серверу аутентификации, кто такой пользователь
	 */
	public class Authenticator extends TCPHandler {
		public Authenticator(Socket socket) throws IOException {
			super(socket);
		}
		
		@Override
		public void run() {
			LOGGER.debug("Начало аутентификации на сервере");
			
			// логин-пароль
			Packet packet = new UserLogin(login, password);
			sendData(packet);
			
			// координаты сервера приложений
			packet = receiveData();
			packet = packet.process(Client.this);
			
			// координаты клиента
			sendData(packet);
			
			LOGGER.debug("Окончание аутентификации на сервере");
		}
	}
}