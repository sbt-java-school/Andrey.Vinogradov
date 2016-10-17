package ru.sbt.home.task14;

import ru.sbt.home.task14.messages.Message;
import ru.sbt.home.task14.messages.MessageImpl;
import ru.sbt.home.task14.net.UDPSender;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientActivityListener implements ServerListener {
	public static final String MULTI_HOST = "228.5.6.7";
	public static final int MULTI_PORT = 19080;

	private UDPSender multiSender;

	public ClientActivityListener() throws UnknownHostException, SocketException {
		multiSender = new UDPSender(InetAddress.getByName(MULTI_HOST), MULTI_PORT);
	}

	@Override
	public void onClientConnect(String login) {
		multiSender.sendData(new MessageImpl(Message.SYSTEM, null, "Присоединился пользователь " + login));
	}

	@Override
	public void onClientDisconnect(String login) {
		multiSender.sendData(new MessageImpl(Message.SYSTEM, null, "Отсоединился пользователь " + login));
	}
}