package org.roof.web.log.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;
import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 登录日志 <br/>
 *         表名： s_login_log <br/>
 *         描述：s_login_log <br/>
 */
@Table(name = "s_login_log")
public class LoginLog implements Serializable {

	public static final String FAIL = "fail";// 登录状态：失败
	public static final String SUCCESS = "success"; // 登录状态：成功

	private static final long serialVersionUID = -1174426671067172248L;
	protected Long id;// 主键
	protected String ip;// 登录ip
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date login_time;// 登录时间
	protected String stat;// 状态
	protected String username;// 登录用户名
	protected String errorMsg;

	public LoginLog() {
		super();
	}

	public LoginLog(Long id) {
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLogin_time() {
		return login_time;
	}

	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	
	
	
}
