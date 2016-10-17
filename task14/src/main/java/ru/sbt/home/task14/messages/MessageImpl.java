package ru.sbt.home.task14.messages;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * Реализация сообщений чата
 */
public class MessageImpl implements Message {
	private String sender;
	private String receiver;
	private String message;
	
	private byte[] bytes = new byte[BYTE_SIZE];
	
	/**
	 * Конструктор на строках, используется на клиенте
	 * сразу собирается байтовый массив, поскольку класс иммутабельный
	 *
	 * @param sender   отправитель (текущий клиент)
	 * @param receiver получатель
	 * @param message  сообщение
	 */
	public MessageImpl(String sender, String receiver, String message) {
		this.sender = trim(sender, NAME_SIZE);
		this.receiver = trim(receiver, NAME_SIZE);
		this.message = trim(message, TEXT_SIZE);
		
		this.bytes = bytes();
	}
	
	/**
	 * Конструктор на байтовом массиве, используется на сервере
	 * Исходный байтовый массив сохраняется для повторной отправки получателю
	 *
	 * @param bytes входные байты
	 * @throws UnsupportedEncodingException
	 */
	public MessageImpl(byte[] bytes) throws UnsupportedEncodingException {
		Objects.requireNonNull(bytes);
		
		System.arraycopy(bytes, 0, this.bytes, 0, Math.min(BYTE_SIZE, bytes.length));
		
		sender = trim(new String(bytes, 0, 2 * NAME_SIZE, CHARSET), NAME_SIZE);
		receiver = trim(new String(bytes, 2 * NAME_SIZE, 2 * NAME_SIZE, CHARSET), NAME_SIZE);
		message = trim(new String(bytes, 4 * NAME_SIZE, 2 * TEXT_SIZE, CHARSET), TEXT_SIZE);
	}
	
	/**
	 * Ограничени строк по длине
	 *
	 * @param str    исходная строка
	 * @param length длина для ограничения
	 * @return ограниченная строка
	 */
	private String trim(String str, int length) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		
		if (StringUtils.containsOnly(str, "\u0000")) {
			return null;
		}
		
		return StringUtils.trimToNull(StringUtils.left(str, length));
	}
	
	/**
	 * Сборка байтового массива для пересылки
	 *
	 * @return байтовый массив для пересылки
	 */
	private byte[] bytes() {
		byte[] res = new byte[BYTE_SIZE];
		
		try {
			byte[] tmp;
			if (StringUtils.isNotBlank(sender)) {
				tmp = sender.getBytes(CHARSET);
				System.arraycopy(tmp, 0, res, 0, Math.min(tmp.length, 2 * NAME_SIZE));
			}
			
			if (StringUtils.isNotBlank(receiver)) {
				tmp = receiver.getBytes(CHARSET);
				System.arraycopy(receiver.getBytes(CHARSET), 0, res, 2 * NAME_SIZE, Math.min(tmp.length, 2 * NAME_SIZE));
			}
			
			if (StringUtils.isNotBlank(message)) {
				tmp = message.getBytes(CHARSET);
				System.arraycopy(message.getBytes(CHARSET), 0, res, 4 * NAME_SIZE, Math.min(tmp.length, 2 * TEXT_SIZE));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	@Override
	public String getSender() {
		return sender;
	}
	
	@Override
	public String getReceiver() {
		return receiver;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public byte[] getBytes() {
		byte[] res = new byte[BYTE_SIZE];
		
		System.arraycopy(bytes, 0, res, 0, BYTE_SIZE);
		
		return res;
	}
}