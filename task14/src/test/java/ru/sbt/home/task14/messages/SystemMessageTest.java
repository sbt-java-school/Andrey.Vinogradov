package ru.sbt.home.task14.messages;

import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class SystemMessageTest {
	@Test
	public void test() throws UnsupportedEncodingException {
		Message original = new MessageImpl("long login for test only 1", null, "msg");
		Message copy = new MessageImpl(original.getBytes());
		
		Assert.assertEquals("original.sender == copy.sender", original.getSender(), copy.getSender());
		Assert.assertNull("original.receiver == null", original.getReceiver());
		Assert.assertNull("copy.receiver == null", copy.getReceiver());
		Assert.assertEquals("original.message == copy.message", original.getMessage(), copy.getMessage());
		
		original = new MessageImpl("длинный логин только для теста 1", "длинный логин только для теста 1", "Длииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииииинноооооооооооооооооооооооооооооое сообщение");
		copy = new MessageImpl(original.getBytes());
		
		Assert.assertEquals("original.sender == copy.sender", original.getSender(), copy.getSender());
		Assert.assertEquals("original.receiver == copy.receiver", original.getReceiver(), copy.getReceiver());
		Assert.assertEquals("original.message == copy.message", original.getMessage(), copy.getMessage());
	}
}