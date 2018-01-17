package org.roof.web.role.action;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageUtils;
import org.roof.spring.Result;
import org.roof.web.role.entity.Role;
import org.roof.web.role.service.api.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("roleAction")
public class RoleAction {

	private IRoleService roleService;

	@RequestMapping("/list")
	public String list(@ModelAttribute("role") Role role, HttpServletRequest request, Model model) {
		Page page = PageUtils.createPage(request);
		page = roleService.page(page, role);
		Map<String, Long> pageBar = PageUtils.createPagePar(page);
		model.addAttribute("page", page);
		model.addAttribute("pageBar", pageBar);
		return "/roof-web/web/role/role_list.jsp";
	}

	@RequestMapping("/list_select")
	public String list_select(Role role, HttpServletRequest request, Model model) {
		list(role, request, model);
		return "/roof-web/web/role/role_list_select.jsp";
	}

	@RequestMapping("/create")
	public @ResponseBody Result create(Role role, String allSrc, String selSrc, Model model) {
		if (role != null) {
			role.setCreate_date(new Date());
			roleService.save((Role) role);
			roleService.changeSrc(role, allSrc, selSrc);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/create_page")
	public String create_page() {
		return "/roof-web/web/role/role_create_page.jsp";
	}

	@RequestMapping("/update")
	public @ResponseBody Result update(Role role, String allSrc, String selSrc, Model model) {
		if (role != null) {
			roleService.update(role, allSrc, selSrc);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/update_page")
	public String update_page(Long id, Model model) {
		Role role = roleService.load(id);
		model.addAttribute("role", role);
		return "/roof-web/web/role/role_update_page.jsp";
	}

	@RequestMapping("/delete")
	public @ResponseBody Result delete(Long id, Model model) {
		Role role = new Role();
		role.setId(id);
		roleService.delete(role);
		return new Result("删除成功!");
	}

	public String detail() {
		return null;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	@Autowired(required = true)
	public void setRoleService(@Qualifier("roleService") IRoleService roleService) {
		this.roleService = roleService;
	}

}
