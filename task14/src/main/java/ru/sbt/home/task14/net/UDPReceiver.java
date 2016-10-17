package ru.sbt.home.task14.net;

import ru.sbt.home.task14.ClientActivityListener;
import ru.sbt.home.task14.messages.Message;
import ru.sbt.home.task14.messages.MessageImpl;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;

public class UDPReceiver implements Closeable {
	private InetAddress group;

	private DatagramSocket socket;
	private MulticastSocket multiSocket;

	private DatagramPacket packet;

	private boolean isMulti;

	public UDPReceiver(int port) throws SocketException, UnknownHostException {
		isMulti = false;

		socket = new DatagramSocket(port);
		packet = new DatagramPacket(new byte[Message.BYTE_SIZE], Message.BYTE_SIZE);
	}

	public UDPReceiver(String host, int port) throws IOException {
		isMulti = true;

		group = InetAddress.getByName(host);

		multiSocket = new MulticastSocket(ClientActivityListener.MULTI_PORT);
		multiSocket.joinGroup(group);

		packet = new DatagramPacket(new byte[Message.BYTE_SIZE], Message.BYTE_SIZE);
	}

	public Message receiveData() {
		Message msg = null;

		try {
			if (isMulti) {
				multiSocket.receive(packet);
			} else {
				socket.receive(packet);
			}

			msg = new MessageImpl(packet.getData());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return msg;
	}

	public void close() throws IOException {
		if (isMulti) {
			multiSocket.leaveGroup(group);
			multiSocket.close();
		} else {
			socket.close();
		}
	}
}