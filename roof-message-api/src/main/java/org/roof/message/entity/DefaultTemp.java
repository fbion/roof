package org.roof.message.entity;

import org.roof.message.api.Temp;

/**
 * 默认消息模板
 * 
 * @author liuxin
 *
 */
public class DefaultTemp implements Temp {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5218403207579832091L;
	private Long id;
	private String topic; // 主题
	private String type; // 消息类型
	private String body;// 模板体

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.Temp#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.Temp#getTopic()
	 */
	@Override
	public String getTopic() {
		return topic;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.Temp#getType()
	 */
	@Override
	public String getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.Temp#getBody()
	 */
	@Override
	public String getBody() {
		return body;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.Temp#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.Temp#setTopic(java.lang.String)
	 */
	@Override
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.Temp#setType(java.lang.String)
	 */
	@Override
	public void setType(String type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.message.entity.Temp#setBody(java.lang.String)
	 */
	@Override
	public void setBody(String body) {
		this.body = body;
	}

}
