package ru.sbt.home.task13;

import ru.sbt.home.task13.packets.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class AbstractSocketHandler implements Runnable {
	/**
	 * Сокет для обмена
	 */
	private Socket socket;
	
	protected AbstractSocketHandler(Socket socket) throws IOException {
		this.socket = socket;
	}
	
	/**
	 * Отсылка пакета через сериализацию объекта
	 *
	 * @param packet пакет для отправки
	 */
	protected void sendData(Packet packet) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			
			out.writeObject(packet);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Поучение пакета через десериализацию объекта
	 *
	 * @return полученный пакет
	 */
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
}