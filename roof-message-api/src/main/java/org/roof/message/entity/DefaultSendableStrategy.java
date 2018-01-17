package org.roof.message.entity;

import org.roof.message.api.SendableStrategy;

/**
 * 默认发送策略实现
 * 
 * @author liuxin
 *
 */
public class DefaultSendableStrategy implements SendableStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3454800394193773285L;

	private Long id;
	private String topic; // 主题
	private String type; // 消息类型
	private Integer retryTimes; // 重发次数
	private Integer priority;// 优先级

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.SendableStrategy#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.SendableStrategy#getTopic()
	 */
	@Override
	public String getTopic() {
		return topic;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.SendableStrategy#getType()
	 */
	@Override
	public String getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.SendableStrategy#getRetryTimes()
	 */
	@Override
	public Integer getRetryTimes() {
		return retryTimes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.SendableStrategy#getPriority()
	 */
	@Override
	public Integer getPriority() {
		return priority;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.SendableStrategy#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.SendableStrategy#setTopic(java.lang.String)
	 */
	@Override
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.SendableStrategy#setType(java.lang.String)
	 */
	@Override
	public void setType(String type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.roof.message.entity.SendableStrategy#setRetryTimes(java.lang.Integer)
	 */
	@Override
	public void setRetryTimes(Integer retryTimes) {
		this.retryTimes = retryTimes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.roof.message.entity.SendableStrategy#setPriority(java.lang.Integer)
	 */
	@Override
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

}
