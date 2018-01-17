package org.roof.message.api;

import java.io.Serializable;

/**
 * 发送策略
 * 
 * @author liuxin
 *
 */
public interface SendableStrategy extends Serializable{

	Long getId();

	String getTopic(); // 主题

	String getType(); // 类型

	Integer getRetryTimes(); // 重发次数

	Integer getPriority(); // 优先级

	void setId(Long id);

	void setTopic(String topic);

	void setType(String type);

	void setRetryTimes(Integer retryTimes);

	void setPriority(Integer priority);

}