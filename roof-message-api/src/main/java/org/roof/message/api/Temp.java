package org.roof.message.api;

import java.io.Serializable;

/**
 * 消息模板
 * 
 * @author liuxin
 *
 */
public interface Temp extends Serializable {

	Long getId();

	String getTopic(); // 主题

	String getType(); // 类型

	String getBody(); // 模板体

	void setId(Long id);

	void setTopic(String topic);

	void setType(String type);

	void setBody(String body);

}