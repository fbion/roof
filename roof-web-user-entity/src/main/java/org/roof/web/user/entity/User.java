package org.roof.web.user.entity;

import java.beans.Transient;
import java.util.Date;
import java.util.List;

import org.roof.web.org.entity.Organization;

/**
 * 用户
 * 
 * @author liuxin
 * 
 */
public class User extends BaseUser {
	private static final long serialVersionUID = 1L;
	protected String name;// 用户名称（中文）
	protected Date create_date;// 创建时间
	protected Date update_time; // 信息修改时间
	protected Long login_count;// 成功登录次数
	protected List<Long> orgIds;
	protected Organization org;

	public User() {
		super();
	}

	public User(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public Organization getOrg() {
		return org;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	@Transient
	public List<Long> getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(List<Long> orgIds) {
		this.orgIds = orgIds;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Long getStaff_id() {
		return id;
	}

	public void setStaff_id(Long id) {
		this.id = id;
	}

	public Long getLogin_count() {
		return login_count;
	}

	public void setLogin_count(Long login_count) {
		this.login_count = login_count;
	}



}
