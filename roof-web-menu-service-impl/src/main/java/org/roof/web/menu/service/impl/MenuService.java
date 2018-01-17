package org.roof.web.menu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.roof.web.menu.dao.api.IMenuDao;
import org.roof.web.menu.entity.Menu;
import org.roof.web.menu.entity.MenuType;
import org.roof.web.menu.entity.MenuVo;
import org.roof.web.menu.service.api.IMenuService;
import org.roof.web.resource.dao.api.IResourceDao;
import org.roof.web.resource.entity.Module;
import org.roof.web.resource.entity.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MenuService implements IMenuService {
	private IMenuDao menuDao;
	private IResourceDao resourceDao;

	@Override
	public void update(Menu menu) {
		menuDao.update(menu);
	}

	@Override
	public Menu load(Long id) {
		Menu menu = menuDao.load(Menu.class, id);
		if (menu.getModule() != null && menu.getModule().getId() != null) {
			Privilege privilege = resourceDao.load(Privilege.class, menu.getModule().getId());
			menu.setModule(privilege);
		}
		return menu;
	}

	@Override
	public Menu selectByModule(List<Long> list) {
		return menuDao.selectByModule(list);
	}

	@Override
	public List<MenuVo> read(Long parentId) {
		List<Menu> menus = menuDao.findMenuByParent(parentId, null);
		List<MenuVo> result = new ArrayList<MenuVo>();
		for (Menu menu : menus) {
			MenuVo menuVo = new MenuVo();
			copyProperties(menu, menuVo);
			result.add(menuVo);
		}
		return result;
	}

	private void copyProperties(Menu menu, MenuVo menuVo) {
		menuVo.setId(menu.getId().toString());
		menuVo.setName(menu.getName());
		menuVo.setLeaf(menu.getLeaf());
		Module module = menu.getModule();
		menuVo.setTitle((module == null) ? menu.getName() : module.getPath());
	}

	@Override
	public Menu create(Long parentId, Menu menu) {
		if (parentId == null || parentId.longValue() == 0L) {
			parentId = 1L;
		}
		if (menu.getModule() != null && menu.getModule().getId() == null) {
			menu.setModule(null);
		}
		Menu parent = menuDao.load(Menu.class, parentId);
		if (parent.getLeaf() != null && parent.getLeaf()) {
			parent.setLeaf(false);
			menuDao.update(parent);
		}
		menu.setParent(parent);
		menu.setLvl(parent.getLvl() + 1);
		menu.setLeaf(true);
		if (menu.getMenuType() == null) {
			menu.setMenuType(MenuType.BackGround);
		}
		menuDao.save(menu);
		return menu;
	}

	@Override
	public void delete(Long id) {
		Menu menu = menuDao.load(Menu.class, id);
		Menu parent = (Menu) menu.getParent();
		Long count = menuDao.childrenCount(parent.getId());
		if (count == 1) {
			parent.setLeaf(true);
			menuDao.updateIgnoreNull(parent);
		}
		menu.setId(id);
		menuDao.delete(menu);
	}

	@Override
	public Long childrenCount(Long parentId) {
		return menuDao.childrenCount(parentId);
	}

	@Autowired(required = true)
	public void setMenuDao(@Qualifier("menuDao") IMenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@Autowired(required = true)
	public void setResourceDao(@Qualifier("resourceDao") IResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

}
