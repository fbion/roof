package org.roof.web.user.service.api;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.roof.commons.PropertiesUtil;
import org.roof.web.role.entity.BaseRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 UserUtils.java 2012-7-5
 */
public class BaseUserContext {

	/**
	 * 获得当前登录用户
	 * 
	 * @return
	 */
	public static UserDetails getCurrentUser(HttpServletRequest httpServletRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			if (httpServletRequest == null) {
				return null;
			}
			SecurityContextImpl securityContextImpl = (SecurityContextImpl) httpServletRequest.getSession()
					.getAttribute("SPRING_SECURITY_CONTEXT");
			if (securityContextImpl == null) {
				return null;
			}
			authentication = securityContextImpl.getAuthentication();
		}
		if (authentication == null) {
			return null;
		}
		Object o = authentication.getPrincipal();
		if (o instanceof UserDetails) {
			return (UserDetails) o;
		}
		return null;
	}

	/**
	 * 手工存放用户登录信息
	 * 
	 * @param userDetails
	 */
	public static void putCurrentUser(UserDetails userDetails, HttpServletRequest request) {

		// 根据用户名username加载userDetails
		// UserDetails userDetails =
		// userDetailsService.loadUserByUsername(username);

		// 根据userDetails构建新的Authentication,这里使用了
		// PreAuthenticatedAuthenticationToken当然可以用其他token,如UsernamePasswordAuthenticationToken

		Collection<BaseRole> authorities = (Collection<BaseRole>) userDetails.getAuthorities();
		try {
			if (authorities == null) {
				authorities = new ArrayList<BaseRole>();
			}
			BaseRole grantedAuthority = new BaseRole(Long.valueOf(PropertiesUtil.getPorpertyString("core.loginuser")));
			authorities.add(grantedAuthority);
		} catch (Exception e) {
			e.printStackTrace();
		}

		PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(userDetails,
				userDetails.getPassword(), authorities);

		// 设置authentication中details
		authentication.setDetails(new WebAuthenticationDetails(request));

		// 存放authentication到SecurityContextHolder
		SecurityContextHolder.getContext().setAuthentication(authentication);
		HttpSession session = request.getSession(true);
		// 在session中存放security context,方便同一个session中控制用户的其他操作
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
	}

	/**
	 * 获取当前用户的授权
	 * 
	 * @return
	 */
	public static String[] getUserAuthorities(HttpServletRequest request) {
		Collection<? extends GrantedAuthority> authorities = getCurrentUser(request).getAuthorities();
		if (authorities == null) {
			return null;
		}
		String[] result = new String[authorities.size()];
		int i = 0;
		for (GrantedAuthority grantedAuthority : authorities) {
			result[i] = grantedAuthority.getAuthority();
			i++;
		}
		return result;
	}

	/**
	 * 获取当前用户的角色
	 * 
	 * @return 角色ID数组
	 */
	public static Long[] getUserRoles(HttpServletRequest request) {
		if (getCurrentUser(request) == null) {
			return null;
		}
		Collection<? extends GrantedAuthority> authorities = getCurrentUser(request).getAuthorities();
		if (authorities == null) {
			return null;
		}
		Long[] result = new Long[authorities.size()];
		int i = 0;
		for (GrantedAuthority grantedAuthority : authorities) {
			BaseRole baseRole = (BaseRole) grantedAuthority;
			result[i] = baseRole.getId();
			i++;
		}
		return result;
	}
}
