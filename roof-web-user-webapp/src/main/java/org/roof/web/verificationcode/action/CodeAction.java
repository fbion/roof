package org.roof.web.verificationcode.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.roof.web.verificationcode.service.api.VerificationImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("codeAction")
public class CodeAction {
	private Logger LOG = Logger.getLogger(CodeAction.class);

	@RequestMapping("image")
	public synchronized void image(HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		// 如果开启Hard模式，可以不区分大小写
		// String securityCode =
		// SecurityCode.getSecurityCode(4,SecurityCodeLevel.Hard,
		// false).toLowerCase();

		// 获取默认难度和长度的验证码
		// String securityCode = VerificationCode.getSecurityCode();
		String securityCode = "0000";
		ByteArrayInputStream in = null;
		try {
			in = VerificationImage.getImageAsInputStream(securityCode);
			// 放入session中
			httpSession.setAttribute("SESSION_SECURITY_CODE", securityCode);
			response.setContentType("image/png");
			OutputStream out = response.getOutputStream();
			byte[] buf = new byte[1024];
			while (in.read(buf) != -1) {
				out.write(buf);
			}
			out.flush();
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
		}

	}
}
