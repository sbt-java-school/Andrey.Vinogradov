package ru.sbt.home.task14;

public interface ServerListener {
	public void onClientConnect(String login);

	public void onClientDisconnect(String login);
}