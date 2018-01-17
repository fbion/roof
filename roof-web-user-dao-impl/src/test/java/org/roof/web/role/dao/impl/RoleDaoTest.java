package org.roof.web.role.dao.impl;

import java.util.List;

import org.junit.Test;
import org.roof.roof.dataaccess.api.Page;
import org.roof.web.role.dao.api.IRoleDao;
import org.roof.web.role.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class RoleDaoTest extends AbstractJUnit4SpringContextTests {
	private IRoleDao roleDao;

//	@Test
//	public void testPage() {
//		Role params = new Role(null, "登录用户");
//		Page page = new Page();
//		page = roleDao.page(page, params);
//		System.out.println(page.getDataList());
//	}
//
//	@Test
//	public void testListVo() {
//		Role params = new Role(null, "登录用户");
//		List<Role> roles = roleDao.listVo(params);
//		for (Role role : roles) {
//			System.out.println(role);
//		}
//	}

	@Autowired
	public void setRoleDao(@Qualifier("roleDao") IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

}
