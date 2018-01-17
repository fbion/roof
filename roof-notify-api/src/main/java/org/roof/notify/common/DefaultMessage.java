package org.roof.notify.common;

import org.roof.notify.api.Message;

/**
 * 默认消息
 * 
 * @author liuxin
 *
 */
public class DefaultMessage implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2663017847128079243L;

	private String topic;
	private Object body;

	public DefaultMessage(String topic, Object body) {
		this.topic = topic;
		this.body = body;
	}

	public String getTopic() {
		return topic;
	}

	public Object getBody() {
		return body;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setBody(Object body) {
		this.body = body;
	}

}
