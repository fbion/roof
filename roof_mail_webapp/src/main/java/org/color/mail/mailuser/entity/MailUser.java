package org.color.mail.mailuser.entity;

import java.io.Serializable;

import javax.persistence.Id;

import org.roof.commons.Md5Generator;

/**
 * @author 模版生成 <br/>
 *         表名： t_mail_user <br/>
 *         描述：t_mail_user <br/>
 */
public class MailUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7492087529662414970L;
	protected Long id;// id
	protected String username;// username
	protected String mailMD5;
	protected String mail;// mail
	protected Boolean enabled; // 是否可用
	protected Integer fail_count; // 失败次数

	public MailUser() {
		super();
	}

	public MailUser(Long id) {
		super();
		this.id = id;
	}

	@Id // 主键
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public Integer getFail_count() {
		return fail_count;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setFail_count(Integer fail_count) {
		this.fail_count = fail_count;
	}

	public String getMailMD5() {
		return this.mailMD5;
	}

	public void setMailMD5(String mailMD5) {
		this.mailMD5 = mailMD5;
	}

}
