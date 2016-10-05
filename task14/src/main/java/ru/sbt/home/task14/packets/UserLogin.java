package ru.sbt.home.task14.packets;

import ru.sbt.home.task14.AuthServer;
import ru.sbt.home.task14.utils.LoginPass;

public class UserLogin implements Packet<LoginPass, AuthServer.Authenticator> {
	private static final long serialVersionUID = 1L;
	
	private LoginPass userData;
	
	public UserLogin(LoginPass userData) {
		this.userData = userData;
	}
	
	public LoginPass getQuery() {
		return userData;
	}
	
	public Packet process(AuthServer.Authenticator context) {
		return new ServerEncryptionKey();
	}
}