package org.roof.web.menu.service.impl;

import org.junit.Test;
import org.roof.web.menu.dao.api.IMenuDao;
import org.roof.web.menu.entity.Menu;
import org.roof.web.menu.entity.MenuType;
import org.roof.web.menu.entity.MenuVo;
import org.roof.web.menu.service.api.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class MenuServiceTest extends AbstractJUnit4SpringContextTests {
	private IMenuService menuService;
	private IMenuDao menuDao;

	@Test
	public void testRead() {
		Menu menu = new Menu();
		menu.setName("系统管理");
		menu.setLeaf(true);
		menu.setLvl(1);
		menu.setMenuType(MenuType.BackGround);
		// menuService.create(1L, menu);

		System.out.println(menuDao.load(Menu.class, 2L).getName()+"========");
	}

	@Autowired(required = true)
	public void setMenuService(@Qualifier("menuService") IMenuService menuService) {
		this.menuService = menuService;
	}

	@Autowired(required = true)
	public void setMenuDao(@Qualifier("menuDao") IMenuDao menuDao) {
		this.menuDao = menuDao;
	}
}
