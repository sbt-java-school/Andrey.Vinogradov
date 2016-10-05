package ru.sbt.home.task14.utils;

import java.io.Serializable;

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