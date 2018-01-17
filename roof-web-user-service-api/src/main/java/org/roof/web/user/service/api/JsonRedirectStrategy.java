package org.roof.web.user.service.api;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.roof.spring.Result;
import org.springframework.security.web.DefaultRedirectStrategy;

public class JsonRedirectStrategy extends DefaultRedirectStrategy {

	@Override
	public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		String resultStr = "用户名或者密码错误";
		Result r = null;
		String requestType = request.getHeader("X-Requested-With");
		if (StringUtils.equalsIgnoreCase("XMLHttpRequest", requestType)) { // 是否为ajax请求
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			if (StringUtils.equals(url, "/error")) {
				r = new Result(Result.FAIL, new String(resultStr.getBytes(), "utf-8"));
			} else {
				String basePath = request.getContextPath();
				if (!StringUtils.startsWith(url, "http://") && !StringUtils.startsWith(url, basePath)) {
					url = basePath + url;
				}
				r = new Result(Result.SUCCESS, url);
			}

			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(Result.getStr(r).getBytes("utf-8"));
			outputStream.flush();
			return;
		}
		super.sendRedirect(request, response, url);
	}

}
