package org.roof.web.role.entity;

import java.util.Date;

public class Role extends BaseRole {

	private static final long serialVersionUID = -4902550150737877897L;
	private Date create_date; // 创建日期
	private String description; // 描述

	public Role() {
	}

	public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public String getDescription() {
		return description;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
