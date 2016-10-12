package ru.sbt.home.task14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbt.home.task14.messages.Message;
import ru.sbt.home.task14.messages.MessageImpl;
import ru.sbt.home.task14.packets.Packet;
import ru.sbt.home.task14.packets.UserLogin;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Клиент
 * Занимается аутентификацией, рассылкой своих сообщений, приемом обычных сообщений и мультикастов
 */
public class Client {
	public static final int UDP_PORT = 9070;
	public static final String HOST = "localhost";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
	UDPSender messenger;
	private String login;
	private String password;
	private InetAddress serverAddress;
	private ExecutorService commonService;
	private ExecutorService multiService;
	private List<UDPReceiver> receivers = new LinkedList<>();
	
	public Client(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	public static void main(String[] args) throws IOException {
		Client client = new Client("логин", "пароль");
		
		client.start();
		client.authenticate();
		
		client.sendMessage(client.login, "Привет1");
		client.sendMessage(client.login, "Привет2");
		client.sendMessage(client.login, "Привет3");
		client.sendMessage(client.login, "Привет4");
		client.sendMessage(client.login, "Привет5");
		
		client.stop();
	}
	
	public void start() throws SocketException, UnknownHostException {
		multiService = Executors.newSingleThreadExecutor();
		multiService.submit(() -> {
			try {
				InetAddress group = InetAddress.getByName(Server.MULTI_HOST);
				
				MulticastSocket multiSocket = new MulticastSocket(Server.MULTI_PORT);
				multiSocket.joinGroup(group);
				
				DatagramPacket packet = new DatagramPacket(new byte[Message.BYTE_SIZE], Message.BYTE_SIZE);
				
				while (true) {
					multiSocket.receive(packet);
					Message msg = new MessageImpl(packet.getData());
					
					LOGGER.debug(msg.getSender() + " >> " + msg.getMessage());
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		commonService = Executors.newSingleThreadExecutor();
		commonService.submit(() -> {
			try {
				UDPReceiver handler = new UDPReceiver(UDP_PORT);
				
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
		
		messenger = new UDPSender(serverAddress, Server.UDP_PORT);
		
		LOGGER.debug("Клиент запущен");
	}
	
	public void stop() {
		multiService.shutdown();
		commonService.shutdown();
		
		LOGGER.debug("Клиент остановлен");
	}
	
	public void authenticate() throws IOException {
		Authenticator auth = new Authenticator();
		auth.run();
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
		public Authenticator() throws IOException {
			super(new Socket(HOST, Server.TCP_PORT));
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