package org.roof.web.user.dao.impl;

import java.util.List;

import org.junit.Test;
import org.roof.roof.dataaccess.api.Page;
import org.roof.web.user.dao.api.IUserDao;
import org.roof.web.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class UserDaoTest extends AbstractJUnit4SpringContextTests {
	private IUserDao userDao;

//	@Test
//	public void testReadUserCount() {
//		User user = new User();
//		user.setUsername("admin");
//		long l = userDao.readUserCount(user);
//		System.out.println(l);
//	}
//
//	@Test
//	public void testPage() {
//		Page page = new Page();
//		User user = new User();
//		user.setUsername("admin");
//		page = userDao.page(page, user);
//		System.out.println(page.getDataList());
//	}
//
//	@Test
//	public void testList() {
//		User user = new User();
//		user.setUsername("admin");
//		List<User> list = userDao.list(user);
//		System.out.println(list);
//	}

	@Autowired(required = true)
	public void setUserDao(@Qualifier("userDao") IUserDao userDao) {
		this.userDao = userDao;
	}
}
