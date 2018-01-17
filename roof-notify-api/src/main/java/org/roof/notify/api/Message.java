package org.roof.notify.api;

import java.io.Serializable;

/**
 * 消息，Producer与Consumer使用
 * 
 * @author liuxin
 *
 */
public interface Message extends Serializable {
	/**
	 * 主题
	 */
	String getTopic();

	/**
	 * 消息体
	 */
	Object getBody();

	void setTopic(String topic);

	void setBody(Object body);

}
