package org.roof.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.roof.web.menu.entity.Menu;
import org.roof.web.menu.service.api.IMenuFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("mainAction")
public class MainAction {
	private IMenuFilter menuFilter;

	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request, HttpSession httpSession) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Menu menu = menuFilter.doFilter(1L, new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
				authentication.getCredentials(), authentication.getAuthorities()));
		model.addAttribute("menus", menu.getChildren());
		return "/user_main_auto.jsp";
	}

	@Autowired(required = true)
	public void setMenuFilter(@Qualifier("menuFilter") IMenuFilter menuFilter) {
		this.menuFilter = menuFilter;
	}

}
