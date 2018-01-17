package org.roof.web.org.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.roof.web.org.dao.api.IOrgDao;
import org.roof.web.org.entity.Organization;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class OrgDaoTest extends AbstractJUnit4SpringContextTests {
	private IOrgDao orgDao;
//
//	@Test
//	public void testFindOrgByParent() {
//		List<Organization> orgs = orgDao.findOrgByParent(1L);
//		for (Organization organization : orgs) {
//			System.out.println(organization.getName());
//		}
//	}
//
//	@Test
//	public void testDisable() {
//	}
//
//	@Test
//	public void testChildrenCount() {
//		long l = orgDao.childrenCount(1L);
//		System.out.println(l);
//	}

	@Resource
	public void setOrgDao(IOrgDao orgDao) {
		this.orgDao = orgDao;
	}

}
