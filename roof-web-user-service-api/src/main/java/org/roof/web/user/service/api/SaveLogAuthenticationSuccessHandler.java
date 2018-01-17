package org.roof.web.user.service.api;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.roof.commons.RoofIPUtils;
import org.roof.web.log.entity.LoginLog;
import org.roof.web.log.service.api.ILoginLogService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 登录成功保存日志
 * 
 * @author liuxin
 *
 */
public class SaveLogAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private ILoginLogService loginLogService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		LoginLog loginLog = new LoginLog();
		loginLog.setIp(RoofIPUtils.getIp(request));
		loginLog.setUsername(authentication.getName());
		loginLog.setLogin_time(new Date());
		loginLog.setStat(LoginLog.SUCCESS);
		loginLogService.save(loginLog);
	}

	public ILoginLogService getLoginLogService() {
		return loginLogService;
	}

	public void setLoginLogService(ILoginLogService loginLogService) {
		this.loginLogService = loginLogService;
	}
}
