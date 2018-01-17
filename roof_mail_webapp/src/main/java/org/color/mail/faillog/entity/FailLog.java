package org.color.mail.faillog.entity;

import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 模版生成 <br/>
 *         表名： t_fail_log <br/>
 *         描述：t_fail_log <br/>
 */
public class FailLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4412077656491139045L;
	// 需要手动添加非默认的serialVersionUID
	protected Long id;// 主键
	protected String mail;// 接收邮箱
	protected Long mail_id;// 邮件ID
	protected String msg;// 发送结果
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Date create_time;// 创建时间

	public FailLog() {
		super();
	}

	public FailLog(Long id) {
		super();
		this.id = id;
	}
	
	@Id// 主键
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public Long getMail_id() {
		return mail_id;
	}
	public void setMail_id(Long mail_id) {
		this.mail_id = mail_id;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
}
