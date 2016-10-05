package ru.sbt.home.task14.packets;

import ru.sbt.home.task14.AuthServer;

import java.io.Serializable;

public class ClientEncryptionKey implements Packet<Serializable, AuthServer.Authenticator> {
	private static final long serialVersionUID = 1L;
	
	public Serializable getQuery() {
		return null;
	}
	
	public Packet process(AuthServer.Authenticator context) {
		return new AppServerData(context.pickAppServer());
	}
}