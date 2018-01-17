package org.roof.web.user.service.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

/**
 * 登录认证失败处理链
 * 
 * @author liuxin
 *
 */
public class ChainAuthenticationFailureHandler extends ExceptionMappingAuthenticationFailureHandler {

	private List<AuthenticationFailureHandler> authenticationFailureHandlers;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		if (authenticationFailureHandlers != null) {
			for (AuthenticationFailureHandler authenticationFailureHandler : authenticationFailureHandlers) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
			}
		}
		super.onAuthenticationFailure(request, response, exception);
	}

	public List<AuthenticationFailureHandler> getAuthenticationFailureHandlers() {
		return authenticationFailureHandlers;
	}

	public void setAuthenticationFailureHandlers(List<AuthenticationFailureHandler> authenticationFailureHandlers) {
		this.authenticationFailureHandlers = authenticationFailureHandlers;
	}

}
