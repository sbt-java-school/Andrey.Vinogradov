package ru.sbt.home.task14.packets;

import ru.sbt.home.task14.Server;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Пакет для передачи адреса пользователя
 */
public class UserAddress implements Packet<InetAddress, Server> {
	private static final long serialVersionUID = 1L;
	
	private String login;
	private InetAddress address;
	
	public UserAddress(String login, InetAddress address) {
		this.login = login;
		this.address = address;
	}
	
	@Override
	public InetAddress getQuery() {
		return address;
	}
	
	@Override
	public Packet process(Server context) {
		try {
			context.addUser(login, address);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}