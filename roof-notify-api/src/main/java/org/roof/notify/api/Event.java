package org.roof.notify.api;

import java.io.Serializable;

/**
 * 事件
 * 
 * @author liuxin
 *
 */
public class Event implements Serializable {

	private static final long serialVersionUID = -6743348167110444084L;
	protected String id;// 事件编码
	protected String name;// 事件名称
	protected Event cause; // 事件原因
	protected long when; // 产生时间
	protected Object target; // 事件源
	protected Object data; // 事件数据
	protected String message;// 信息

	public Event getCause() {
		return cause;
	}

	public long getWhen() {
		return when;
	}

	public Object getTarget() {
		return target;
	}

	public Object getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	public void setCause(Event cause) {
		this.cause = cause;
	}

	public void setWhen(long when) {
		this.when = when;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
