package org.roof.web.org.action;

import java.util.List;

import org.roof.spring.Result;
import org.roof.web.org.entity.OrgVo;
import org.roof.web.org.entity.Organization;
import org.roof.web.org.service.api.IOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("orgAction")
public class OrgAction {

	private IOrgService orgService;

	@RequestMapping("/index")
	public String index(Model model) {
		return "/roof-web/web/org/org_index.jsp";
	}

	@RequestMapping("/read")
	public @ResponseBody List<OrgVo> read(@RequestParam(required = false) Long id) {
		return orgService.read(id);
	}

	@RequestMapping("/detail")
	public String detail(@RequestParam Long id, Model model) {
		Organization org = orgService.load(id);
		model.addAttribute("org", org);
		return "/roof-web/web/org/org_detail.jsp";
	}

	@RequestMapping("/create")
	public @ResponseBody Result create(@RequestParam Long parentId, @ModelAttribute("org") Organization org) {
		if (org != null) {
			orgService.create(parentId, org);
			return new Result("保存成功!");
		}
		return new Result("数据传输失败!");
	}

	@RequestMapping("/create_page")
	public String create_page(@RequestParam Long parentId, Model model) {
		if (parentId == null || parentId.longValue() == 0L) {
			parentId = 1L;
		}
		model.addAttribute("parentId", parentId);
		return "/roof-web/web/org/org_create_page.jsp";
	}

	@RequestMapping("/delete")
	public @ResponseBody Result delete(@RequestParam Long id) {
		orgService.delete(id);
		return new Result("删除成功!");
	}

	@RequestMapping("/update")
	public @ResponseBody Result update(@ModelAttribute("org") Organization org) {
		if (org != null) {
			orgService.updateIgnoreNull(org);
			return new Result("保存成功!");
		}
		return new Result("数据传输失败!");
	}

	@RequestMapping("/query")
	public @ResponseBody Object query(@ModelAttribute("org") Organization org) {
		if (org != null) {
			return orgService.selectForList(org);
		}
		return new Result("数据传输失败!");
	}

	@Autowired(required = true)
	public void setOrgService(@Qualifier("orgService") IOrgService orgService) {
		this.orgService = orgService;
	}

}
