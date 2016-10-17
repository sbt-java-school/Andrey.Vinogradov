package ru.sbt.home.task14.net;

import ru.sbt.home.task14.messages.Message;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;
import java.util.Objects;

public class UDPSender implements Closeable {
	private DatagramSocket socket;
	private DatagramPacket packet;

	public UDPSender(InetAddress address, int port) throws SocketException, UnknownHostException {
		Objects.requireNonNull(address);

		socket = new DatagramSocket();
		packet = new DatagramPacket(new byte[Message.BYTE_SIZE], Message.BYTE_SIZE, address, port);
	}

	public void sendData(Message msg) {
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

	public void close() {
		socket.close();
	}
}