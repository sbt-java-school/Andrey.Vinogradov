package ru.sbt.home.task14.packets;

import ru.sbt.home.task14.Client;
import ru.sbt.home.task14.Server;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Пакет для передачи логина-пароля при аутентификации
 */
public class UserLogin implements Packet<UserLogin.LoginPass, Server> {
	private static final long serialVersionUID = 1L;
	
	private LoginPass userData;
	
	public UserLogin(String login, String password) {
		userData = new LoginPass(login, password);
	}
	
	public LoginPass getQuery() {
		return userData;
	}
	
	public Packet process(Server context) {
		Packet packet = null;
		
		try {
			packet = new ServerAddress(InetAddress.getByName(Client.HOST));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return packet;
	}
	
	public class LoginPass implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String login;
		private transient String password;
		private String passwordHash;
		
		public LoginPass(String login, String password) {
			this.login = login;
			this.password = password;
			this.passwordHash = md5(password);
		}
		
		private String md5(String source) {
			String result = source;
			
			// TODO: хэш на пароль
			
			return result;
		}
		
		public String getLogin() {
			return login;
		}
		
		public String getPassword() {
			return password;
		}
		
		public String getPasswordHash() {
			return passwordHash;
		}
	}
}