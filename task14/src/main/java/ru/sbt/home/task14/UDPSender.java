package ru.sbt.home.task14;

import ru.sbt.home.task14.messages.Message;

import java.io.IOException;
import java.net.*;
import java.util.Objects;

public class UDPSender {
	private InetAddress address;
	private DatagramSocket socket;
	private DatagramPacket packet;
	
	protected UDPSender(InetAddress address, int port) throws SocketException, UnknownHostException {
		Objects.requireNonNull(address);

		this.address = address;
		socket = new DatagramSocket();
		packet = new DatagramPacket(new byte[Message.BYTE_SIZE], Message.BYTE_SIZE, address, port);
	}
	
	protected void sendData(Message msg) {
		if (packet == null) {
			return;
		}
		
		Objects.requireNonNull(msg);
		
		packet.setData(msg.getBytes());
		
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}