package org.roof.web.dictionary.dao.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.roof.web.dictionary.dao.api.IDictionaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class DictionaryDaoTest extends AbstractJUnit4SpringContextTests {
	private IDictionaryDao dictionaryDao;

//	@Test
//	public void testFindByType() {
//		dictionaryDao.findByType("aall");
//	}
//
//	@Test
//	public void testFindChildrenCount() {
//		dictionaryDao.findChildrenCount("DIC");
//	}
//
//	@Test
//	public void testLoad() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testLoadByTypeText() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQuery() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQuery2() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetDaoSupport() {
//		fail("Not yet implemented");
//	}

	@Autowired(required = true)
	public void setDictionaryDao(
			@Qualifier("dictionaryDao") IDictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}

}
