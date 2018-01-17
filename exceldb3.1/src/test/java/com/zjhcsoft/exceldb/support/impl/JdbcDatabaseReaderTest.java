	package com.zjhcsoft.exceldb.support.impl;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.zjhcsoft.exceldb.entity.XslDb;

/**
 * 
 * @author liuxin 2011-4-12
 * 
 */
public class JdbcDatabaseReaderTest {
	@Test
	public void testRead() {
		XslDb xslDb = MappingLoader.getXslDb("ASS_KPI_ACCEPT_BASE_DATA");
		JdbcDatabaseReader databaseReader = new JdbcDatabaseReader();
		databaseReader.setXslDb(xslDb);
		while (databaseReader.hasNext()) {
			List<Map<String, Object>> list = databaseReader.next();
			for (Map<String, Object> map : list) {
				System.out.println(map);
			}
		}
	}

	@Test
	public void testRead2() {
		JdbcDatabaseReader databaseReader = new JdbcDatabaseReader();
		databaseReader.setQuerySql("select * from ASS_KPI_ACCEPT_BASE_DATA");
		while (databaseReader.hasNext()) {
			List<Map<String, Object>> list = databaseReader.next();
			for (Map<String, Object> map : list) {
				System.out.println(map);
			}
		}
	}
	
	@Test
	public void testRead3() {
		IbatisDao dao = new IbatisDao();
	}

}
