package org.roof.web.resource.service.impl;

import java.util.List;

import org.junit.Test;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class ResourceServiceTest extends AbstractJUnit4SpringContextTests {
	private IDaoSupport daoSupport;

//	@Test
//	public void testQuery() {
//		String hql = "from Module where 1=1 and parent.id = ? order by seq";
//		List<?> list = daoSupport.findForList(hql, 1L);
//		for (Object object : list) {
//			System.out.println(object);
//		}
//
//	}

	@Test
	public void testFindModuleByPathString() {
	}

	@Test
	public void testFindModuleByPathStringArray() {
	}

	@Test
	public void testRead() {
	}

	@Test
	public void testReadByRole() {
	}

	@Test
	public void testCopyProperties() {
	}

	@Test
	public void testCreate() {
	}

	@Test
	public void testUpdate() {
	}

	@Test
	public void testDelete() {
	}

	@Autowired(required = true)
	public void setDaoSupport(
			@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

}
