package org.roof.web.resource.entity;

/**
 * 参数
 * 
 * @author liuxin
 * 
 */
public class Parameter {
	protected String id;
	protected String name;
	protected Boolean required; // 必须
	protected String type;// 类型
	protected String explain; // 说明

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Boolean getRequired() {
		return required;
	}

	public String getType() {
		return type;
	}

	public String getExplain() {
		return explain;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

}
