package org.roof.spring.web.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 见basePath 加入每个请求的Model中
 * 
 * 
 * @author liuxin 2011-3-28
 * 
 */
public class BasePathHandlerInterceptor extends HandlerInterceptorAdapter {
	private Map<String, String> values;

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			modelAndView.addObject("basePath", request.getContextPath());
			if (values != null) {
				modelAndView.addAllObjects(values);
			}
		}
	}

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

}
