package org.roof.message.api;

import java.io.Serializable;
import java.util.Date;

/**
 * 可发送消息
 * 
 * @author liuxin
 *
 */
public interface Sendable extends Serializable {

	String getId();// 唯一码

	String getSender();// 发送者

	String getReceiver();// 接收者

	String getType();// 消息类型

	String getTopic();// 主题

	String getTags();// 标签

	String getEvent(); // 事件

	Date getSendTime();// 发送时间

	Date getCreateTime();// 创建时间

	Integer getSendTimes(); // 发送次数

	Integer getPriority();// 优先级

	String getContent();// 消息体

	int getStatus();// 状态 -1:发送失败, 0:新建, 1:发送成功, 2:发送中

	String getErrorMsg();// 出错信息

	void setId(String id);

	void setSender(String from);

	void setReceiver(String to);

	void setType(String type);

	void setTopic(String topic);

	void setTags(String tags);

	void setEvent(String event);

	void setSendTime(Date sendTime);

	void setCreateTime(Date createTime);

	void setSendTimes(Integer sendTimes);

	void setPriority(Integer priority);

	void setContent(String content);

	void setStatus(int status);

	void setErrorMsg(String errorMsg);

}