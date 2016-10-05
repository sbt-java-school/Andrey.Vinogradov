package ru.sbt.home.task14.packets;

import ru.sbt.home.task14.Client;

public class AppServerData implements Packet<String, Client.Authenticator> {
	private static final long serialVersionUID = 1L;
	
	private String serverAddress;
	
	public AppServerData(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	
	public String getQuery() {
		return serverAddress;
	}
	
	public Packet process(Client.Authenticator context) {
		context.setAppServer();
		
		return null;
	}
}