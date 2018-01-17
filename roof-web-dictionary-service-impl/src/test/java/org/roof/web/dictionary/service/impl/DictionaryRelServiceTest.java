package org.roof.web.dictionary.service.impl;

import java.util.ArrayList;

import org.junit.Test;
import org.roof.web.dictionary.entity.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import junit.framework.Assert;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class DictionaryRelServiceTest extends AbstractJUnit4SpringContextTests {

	private DictionaryRelService dictionaryRelService;

	@Test
	public void testSaveRel() {
		TestDic testDic = new TestDic();
		testDic.setId(1L);
		testDic.setTime_period(new Dictionary(305L));
		testDic.setRecord_type(new ArrayList<Dictionary>());
		testDic.getRecord_type().add(new Dictionary(337L));
		testDic.getRecord_type().add(new Dictionary(338L));
		testDic.getRecord_type().add(new Dictionary(339L));

		dictionaryRelService.saveRel(testDic);
	}

	@Test
	public void testFill() {
		TestDic testDic = new TestDic();
		testDic.setId(1L);
		dictionaryRelService.fill(testDic);
		Assert.assertEquals(3, testDic.getRecord_type().size());
		Assert.assertNotNull(testDic.getTime_period());
	}

	@Test
	public void testFillByTableName() {
		TestDic testDic = new TestDic();
		testDic.setId(1L);
		dictionaryRelService.fill(testDic, "t_TestDic", 1L);
		Assert.assertEquals(3, testDic.getRecord_type().size());
		Assert.assertNotNull(testDic.getTime_period());
	}

	@Autowired
	public void setDictionaryRelService(DictionaryRelService dictionaryRelService) {
		this.dictionaryRelService = dictionaryRelService;
	}

}
