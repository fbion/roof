package org.roof.web.log.entity;

import java.util.List;

/**
 * @author 模版生成 <br/>
 *         表名： s_login_log <br/>
 *         描述：s_login_log <br/>
 */
public class LoginLogVo extends LoginLog {

	private List<LoginLogVo> loginLogList;

	public LoginLogVo() {
		super();
	}

	public LoginLogVo(Long id) {
		super();
		this.id = id;
	}

	public List<LoginLogVo> getLoginLogList() {
		return loginLogList;
	}

	public void setLoginLogList(List<LoginLogVo> loginLogList) {
		this.loginLogList = loginLogList;
	}

}
