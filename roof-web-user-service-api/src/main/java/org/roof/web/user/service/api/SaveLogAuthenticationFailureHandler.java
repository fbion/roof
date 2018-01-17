package org.roof.web.user.service.api;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.roof.commons.RoofIPUtils;
import org.roof.web.log.entity.LoginLog;
import org.roof.web.log.service.api.ILoginLogService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 登录失败保存日志
 * 
 * @author liuxin
 *
 */
public class SaveLogAuthenticationFailureHandler implements AuthenticationFailureHandler {
	private ILoginLogService loginLogService;
	private String username_parameter = "j_username";

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String username = null;
		if (request.getUserPrincipal() != null) {
			username = request.getUserPrincipal().getName();
		}
		if (StringUtils.isBlank(username)) {
			username = request.getParameter(username_parameter);
		}
		LoginLog loginLog = new LoginLog();
		loginLog.setIp(RoofIPUtils.getIp(request));
		loginLog.setUsername(username);
		loginLog.setLogin_time(new Date());
		loginLog.setStat(LoginLog.FAIL);
		loginLog.setErrorMsg(exception.getMessage());
		loginLogService.save(loginLog);
	}

	public ILoginLogService getLoginLogService() {
		return loginLogService;
	}

	public void setLoginLogService(ILoginLogService loginLogService) {
		this.loginLogService = loginLogService;
	}

	public String getUsername_parameter() {
		return username_parameter;
	}

	public void setUsername_parameter(String username_parameter) {
		this.username_parameter = username_parameter;
	}

}
