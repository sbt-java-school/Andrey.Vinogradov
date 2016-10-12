package ru.sbt.home.task14.packets;

import ru.sbt.home.task14.Client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerAddress implements Packet<InetAddress, Client> {
	private static final long serialVersionUID = 1L;
	
	private InetAddress address;
	
	public ServerAddress(InetAddress address) {
		this.address = address;
	}
	
	public InetAddress getQuery() {
		return address;
	}
	
	public Packet process(Client context) {
		context.setServerAddress(address);
		
		Packet packet = null;
		
		try {
			return new UserAddress(context.getLogin(), InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return packet;
	}
}