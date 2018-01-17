package org.roof.web.resource.dao.impl;

import java.util.List;

import org.junit.Test;
import org.roof.web.resource.dao.api.IResourceDao;
import org.roof.web.resource.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class ResourceDaoTest extends AbstractJUnit4SpringContextTests {
	private IResourceDao resourceDao;

//	@Test
//	public void testFindModuleByParent() {
//		List<Resource> resources = resourceDao.findModuleByParent(null);
//		System.out.println(resources);
//	}
//
//	@Test
//	public void testFindModuleByPathStringArray() {
//		List<Resource> resources = resourceDao
//				.findModuleByPath(new String[] { "t" });
//		System.out.println(resources);
//	}
//
//	@Test
//	public void testChildrenCount() {
//		long l = resourceDao.childrenCount(1L);
//		System.out.println(l);
//	}

	@Autowired(required = true)
	public void setResourceDao(
			@Qualifier("resourceDao") IResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}
}
