package org.roof.web.menu.action;

import java.util.List;

import org.roof.spring.Result;
import org.roof.web.menu.entity.Menu;
import org.roof.web.menu.entity.MenuType;
import org.roof.web.menu.entity.MenuVo;
import org.roof.web.menu.service.api.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("menuAction")
public class MenuAction {
	private IMenuService menuService;

	@RequestMapping("/index")
	public String index() {
		return "/roof-web/web/menu/menu_index.jsp";
	}

	@RequestMapping("/read")
	public @ResponseBody List<MenuVo> read(@RequestParam(required = false) Long id) {
		return menuService.read(id);
	}

	@RequestMapping("/detail")
	public String detail(@RequestParam Long id, Model model) {
		Menu menu = menuService.load(id);
		model.addAttribute("menuTypes", MenuType.values());
		model.addAttribute("menu", menu);
		return "/roof-web/web/menu/menu_detail.jsp";
	}

	@RequestMapping("/create")
	public @ResponseBody Result create(@RequestParam Long parentId, @ModelAttribute("menu") Menu menu) {
		if (menu != null) {
			menuService.create(parentId, menu);
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
		model.addAttribute("menuTypes", MenuType.values());
		return "/roof-web/web/menu/menu_create_page.jsp";
	}

	@RequestMapping("/delete")
	public @ResponseBody Result delete(@RequestParam Long id) {
		menuService.delete(id);
		return new Result("删除成功!");
	}

	@RequestMapping("/update")
	public @ResponseBody Result update(@ModelAttribute("menu") Menu menu) {
		Result result = new Result("数据传输失败!");
		if (menu != null) {
			if (menu.getModule() != null) {
				if (menu.getModule().getId() == null || menu.getModule().getId().longValue() == 0) {
					menu.setModule(null);
				}
			}
			menuService.update(menu);
			result = new Result("保存成功!");
		}
		return result;
	}

	@RequestMapping("/moveTo")
	public @ResponseBody Result moveTo(@RequestParam Long parentId, @RequestParam Long id) {
		Menu parent = menuService.load(parentId);
		Menu menu = menuService.load(id);
		menu.setParent(parent);
		if (menuService.childrenCount(parent.getId()) == 0) {
			parent.setLeaf(true);
		} else {
			parent.setLeaf(false);
		}
		menuService.update(parent);
		menuService.update(menu);
		return new Result("移动成功!");
	}

	@Autowired(required = true)
	public void setMenuService(@Qualifier("menuService") IMenuService menuService) {
		this.menuService = menuService;
	}
}
