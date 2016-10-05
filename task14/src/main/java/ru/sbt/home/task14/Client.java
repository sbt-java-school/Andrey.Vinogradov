package ru.sbt.home.task14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbt.home.task14.packets.Packet;
import ru.sbt.home.task14.packets.UserCertificate;
import ru.sbt.home.task14.utils.LoginPass;

import java.io.IOException;
import java.net.Socket;

public class Client {
	public static final String AUTH_HOST = "localhost";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
	
	private LoginPass userLoginAndPass;
	
	public Client(String login, String pass) {
		userLoginAndPass = new LoginPass(login, pass);
	}
	
	public static void main(String[] args) throws IOException {
		Client client = new Client("login", "pass");
		
		Client.Authenticator auth = client.new Authenticator(new Socket(AUTH_HOST, AuthServer.PORT));
		auth.run();
	}
	
	public class Authenticator extends AbstractSocketHandler {
		public Authenticator(Socket socket) throws IOException {
			super(socket);
		}
		
		public LoginPass getLoginPass() {
			return userLoginAndPass;
		}
		
		public void setAppServer() {
		}
		
		@Override
		public void run() {
			// сертификат пользователя
			Packet packet = new UserCertificate();
			sendData(packet);
			
			// сертификат сервера
			packet = receiveData();
			packet = packet.process(this);
			
			// логин-пароль
			sendData(packet);
			
			// ключ шифрования сервера
			packet = receiveData();
			packet = packet.process(this);
			
			// ключ шифрования клиента
			sendData(packet);
			
			// координаты сервера приложений
			packet = receiveData();
			packet.process(this);
		}
	}
}