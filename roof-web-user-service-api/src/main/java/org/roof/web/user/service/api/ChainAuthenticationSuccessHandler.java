package org.roof.web.user.service.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * 登录认证成功处理链
 * 
 * @author liuxin
 *
 */
public class ChainAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private List<AuthenticationSuccessHandler> authenticationSuccessHandlers;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		if (authenticationSuccessHandlers != null) {
			for (AuthenticationSuccessHandler authenticationSuccessHandler : authenticationSuccessHandlers) {
				authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
			}
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

	public List<AuthenticationSuccessHandler> getAuthenticationSuccessHandlers() {
		return authenticationSuccessHandlers;
	}

	public void setAuthenticationSuccessHandlers(List<AuthenticationSuccessHandler> authenticationSuccessHandlers) {
		this.authenticationSuccessHandlers = authenticationSuccessHandlers;
	}

}
