package org.roof.notify.api;

/**
 * 消息监听器
 * 
 * @author liuxin
 */
public interface MessageListener {

	/**
	 * 收到消息后回调
	 * 
	 * @param message
	 *            消息
	 * @param topic
	 *            主题
	 */
	void onMessage(Message message, String topic);
}
