package ru.sbt.home.task14.messages;

import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class MessageImplTest {
	@Test
	public void test() throws UnsupportedEncodingException {
		Message original = new MessageImpl("1", "2", "msg");
		Message copy = new MessageImpl(original.getBytes());
		
		Assert.assertEquals("original.sender == copy.sender", original.getSender(), copy.getSender());
		Assert.assertEquals("original.receiver == copy.receiver", original.getReceiver(), copy.getReceiver());
		Assert.assertEquals("original.message == copy.message", original.getMessage(), copy.getMessage());
		
		original = new MessageImpl("Длинный логин1", "Длинный логин1", "Длииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииинноооооооооооооооооооооооооооооое сообщение");
		copy = new MessageImpl(original.getBytes());
		
		Assert.assertEquals("original.sender == copy.sender", original.getSender(), copy.getSender());
		Assert.assertEquals("original.receiver == copy.receiver", original.getReceiver(), copy.getReceiver());
		Assert.assertEquals("original.message == copy.message", original.getMessage(), copy.getMessage());
	}
}