package org.roof.notify.api;

/**
 * 消息发送接口
 * 
 * @author liuxin
 *
 */
public interface MessageSender {
	/**
	 * 发送消息
	 * 
	 * @param message
	 *            消息
	 */
	void send(Message message);
}
