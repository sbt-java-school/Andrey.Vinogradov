package ru.sbt.home.task14.net;

import ru.sbt.home.task14.packets.Packet;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public abstract class TCPHandler implements Runnable, Closeable {
	private Socket socket;

	protected TCPHandler(Socket socket) throws IOException {
		this.socket = socket;
	}

	protected void sendData(Packet packet) {
		Objects.requireNonNull(packet);

		try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

			out.writeObject(packet);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected Packet receiveData() {
		Packet packet = null;

		try {
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

			packet = (Packet) in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return packet;
	}

	@Override
	public void close() throws IOException {
		socket.close();
	}
}