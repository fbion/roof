package org.roof.web.org.service.impl;

import org.junit.Test;
import org.roof.web.org.entity.Organization;
import org.roof.web.org.service.api.IOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class OrgServiceTest extends AbstractJUnit4SpringContextTests {
	private IOrgService orgService;

//	@Test
//	public void testLoad() {
//		Organization organization = orgService.load(1L);
//		System.out.println(organization.getName());
//	}
//
//	@Test
//	public void testReadMyNode() {
//	}
//
//	@Test
//	public void testReadLong() {
//	}
//
//	@Test
//	public void testReadListOfOrganization() {
//	}
//
//	@Test
//	public void testCreate() {
//	}
//
//	@Test
//	public void testDelete() {
//	}

	@Autowired(required = true)
	public void setOrgService(@Qualifier("orgService") IOrgService orgService) {
		this.orgService = orgService;
	}

}
