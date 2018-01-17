package org.roof.notify.redis;

import org.roof.notify.common.DefaultMessage;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis消息监听适配器
 * 
 * @author liuxin
 *
 */
public class RedisMessageListenerAdapter implements MessageListener {
	private org.roof.notify.api.MessageListener listener;
	private RedisSerializer<String> redisSerializer = new StringRedisSerializer(); // 消息主题序列化
	private RedisSerializer<Object> valSerializer = new JdkSerializationRedisSerializer(); // 消息体序列化

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String topic = redisSerializer.deserialize(message.getChannel());
		Object body = valSerializer.deserialize(message.getBody());
		org.roof.notify.api.Message msg = new DefaultMessage(topic, body);
		listener.onMessage(msg, topic);
	}

	public org.roof.notify.api.MessageListener getListener() {
		return listener;
	}

	public void setListener(org.roof.notify.api.MessageListener listener) {
		this.listener = listener;
	}

	public RedisSerializer<String> getRedisSerializer() {
		return redisSerializer;
	}

	public RedisSerializer<Object> getValSerializer() {
		return valSerializer;
	}

	public void setRedisSerializer(RedisSerializer<String> redisSerializer) {
		this.redisSerializer = redisSerializer;
	}

	public void setValSerializer(RedisSerializer<Object> valSerializer) {
		this.valSerializer = valSerializer;
	}

}
