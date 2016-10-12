package ru.sbt.home.task14.messages;

/**
 * Основной интерфейс сообщения чата
 * Пересылаемый массив байт состоит из отправителя, получателя и сообщения
 * Длины ограничены из-за UDP протокола
 */
public interface Message {
	public static final int NAME_SIZE = 16;
	public static final int TEXT_SIZE = 150;
	public static final int BYTE_SIZE = 4 * NAME_SIZE + 2 * TEXT_SIZE;
	public static final String CHARSET = "UTF-8";
	
	public static final String SYSTEM = "system";
	
	public String getSender();
	
	public String getReceiver();
	
	public String getMessage();
	
	public byte[] getBytes();
}