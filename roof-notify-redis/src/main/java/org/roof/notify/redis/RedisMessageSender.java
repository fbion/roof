package org.roof.notify.redis;

import org.roof.notify.api.Message;
import org.roof.notify.api.MessageSender;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis消息发送
 * 
 * @author liuxin
 *
 */
public class RedisMessageSender implements MessageSender {

	private RedisTemplate<String, Message> redisTemplate;

	public RedisMessageSender() {
	}

	public RedisMessageSender(RedisTemplate<String, Message> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void send(Message message) {
		redisTemplate.convertAndSend(message.getTopic(), message.getBody());
	}

	public RedisTemplate<String, Message> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Message> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

}
