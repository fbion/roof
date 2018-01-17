package org.roof.notify.redis;

import org.roof.notify.api.Event;
import org.roof.notify.api.Message;
import org.roof.notify.common.DefaultMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

public class RedisMessageSenderTest {

	private static RedisMessageListenerContainer listenerContainer;
	private static RedisMessageSender messageSender;
	private static MessageListener messageListener;
	private static MessageListener messageListener2;

	public static void test() throws InterruptedException {
		listenerContainer.addMessageListener(messageListener, new ChannelTopic("test"));
		listenerContainer.addMessageListener(messageListener2, new ChannelTopic("test2"));
		Thread.sleep(1000 * 3);
		Message message1 = new DefaultMessage("test", "test1");
		messageSender.send(message1);
		Message message2 = new DefaultMessage("test", "test2");
		messageSender.send(message2);
		Message message3 = new DefaultMessage("test", "test3");
		messageSender.send(message3);
		Message message4 = new DefaultMessage("test", "test4");
		messageSender.send(message4);
		Event event = new Event();
		event.setMessage("testMessage");

		Message message5 = new DefaultMessage("test", new Event());
		messageSender.send(message5);

		Message message6 = new DefaultMessage("test2", "test6");
		messageSender.send(message6);

	}

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-redis.xml");
		listenerContainer = (RedisMessageListenerContainer) applicationContext.getBean("messageListenerContainer");
		messageSender = (RedisMessageSender) applicationContext.getBean("redisMessageSender");
		messageListener = (MessageListener) applicationContext.getBean("printMessageListener");
		messageListener2 = (MessageListener) applicationContext.getBean("printMessageListener2");
		test();

	}

}
