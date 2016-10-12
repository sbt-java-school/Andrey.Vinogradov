package ru.sbt.home.task14;

import ru.sbt.home.task14.messages.Message;
import ru.sbt.home.task14.messages.MessageImpl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPReceiver {
	private DatagramSocket socket;
	private DatagramPacket packet;
	
	protected UDPReceiver(int port) throws SocketException, UnknownHostException {
		socket = new DatagramSocket(port);
		packet = new DatagramPacket(new byte[Message.BYTE_SIZE], Message.BYTE_SIZE);
	}
	
	protected Message receiveData() {
		Message msg = null;
		
		try {
			socket.receive(packet);
			
			msg = new MessageImpl(packet.getData());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return msg;
	}
	
	protected void close() {
		socket.close();
	}
}