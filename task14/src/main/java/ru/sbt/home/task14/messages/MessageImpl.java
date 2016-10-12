package ru.sbt.home.task14.messages;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class MessageImpl implements Message {
	private String sender;
	private String receiver;
	private String message;
	
	private byte[] bytes;
	
	public MessageImpl(String sender, String receiver, String message) {
		this.sender = trim(sender, NAME_SIZE);
		this.receiver = trim(receiver, NAME_SIZE);
		this.message = trim(message, TEXT_SIZE);
		
		this.bytes = bytes();
	}
	
	public MessageImpl(byte[] bytes) throws UnsupportedEncodingException {
		Objects.requireNonNull(bytes);
		
		this.bytes = bytes;
		
		sender  = new String(bytes, 0, 2 * NAME_SIZE, CHARSET).trim();
		receiver= new String(bytes, 2 * NAME_SIZE, 2 * NAME_SIZE, CHARSET).trim();
		message = new String(bytes, 4 * NAME_SIZE, 2 * TEXT_SIZE, CHARSET).trim();
	}
	
	private String trim(String str, int length) {
		if (str == null) {
			return null;
		}
		
		return str.length() > length ? str.substring(0, length - 1) : str;
	}
	
	private byte[] bytes() {
		byte[] res = new byte[BYTE_SIZE];
		
		try {
			byte[] tmp = sender.getBytes(CHARSET);
			System.arraycopy(tmp, 0, res, 0, Math.min(tmp.length, 2 * NAME_SIZE));
			
			tmp = receiver.getBytes(CHARSET);
			System.arraycopy(receiver.getBytes(CHARSET), 0, res, 2 * NAME_SIZE, Math.min(tmp.length, 2 * NAME_SIZE));
			
			tmp = message.getBytes(CHARSET);
			System.arraycopy(message.getBytes(CHARSET), 0, res, 4 * NAME_SIZE, Math.min(tmp.length, 2 * TEXT_SIZE));
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
		return bytes;
	}
}