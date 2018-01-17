package org.roof.message.entity;

import java.util.Date;

import org.roof.message.api.Sendable;

/**
 * 信息发送默认实现
 * 
 * @author liuxin
 *
 */
public class DefaultSendable implements Sendable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -139571484457878013L;

	private String id;// 唯一码
	private String sender; // 发送者
	private String receiver;// 接收者
	private String type; // 消息类型
	private String topic; // 主题
	private String tags;// 标签(多个标签用,隔开)
	private String event; // 触发事件
	private Date sendTime; // 发送时间
	private Date createTime;// 创建时间
	private Integer sendTimes; // 发送次数
	private Integer priority;// 优先级
	private String content;// 消息体
	private int status; // 状态 -1:发送失败, 0:新建, 1:发送成功, 2:发送中
	private String errorMsg;// 出错信息

	public DefaultSendable() {
		this.createTime = new Date();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getSender() {
		return sender;
	}

	@Override
	public String getReceiver() {
		return receiver;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getTopic() {
		return topic;
	}

	@Override
	public String getTags() {
		return tags;
	}

	@Override
	public String getEvent() {
		return event;
	}

	@Override
	public Date getSendTime() {
		return sendTime;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public Integer getSendTimes() {
		return sendTimes;
	}

	@Override
	public Integer getPriority() {
		return priority;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setSender(String sender) {
		this.sender = sender;
	}

	@Override
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public void setTags(String tags) {
		this.tags = tags;
	}

	@Override
	public void setEvent(String event) {
		this.event = event;
	}

	@Override
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}

	@Override
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Override
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String getErrorMsg() {
		return errorMsg;
	}

	@Override
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
