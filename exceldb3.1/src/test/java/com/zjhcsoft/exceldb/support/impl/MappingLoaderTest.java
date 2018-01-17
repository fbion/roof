package com.zjhcsoft.exceldb.support.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.zjhcsoft.exceldb.entity.XslDb;

/**
 * 
 * @author liuxin 2011-4-12
 * 
 */
public class MappingLoaderTest {

	@Test
	public void testGetXslDb() {
		XslDb xslDb = MappingLoader.getXslDb("ASS_KPI_ACCEPT_BASE_DATA");
		assertNotNull(xslDb);
	}

}
