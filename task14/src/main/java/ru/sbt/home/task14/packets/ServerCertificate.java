package ru.sbt.home.task14.packets;

import ru.sbt.home.task14.Client;

import java.io.Serializable;

public class ServerCertificate implements Packet<Serializable, Client.Authenticator> {
	private static final long serialVersionUID = 1L;
	
	public Serializable getQuery() {
		return null;
	}
	
	public Packet process(Client.Authenticator context) {
		return new UserLogin(context.getLoginPass());
	}
}