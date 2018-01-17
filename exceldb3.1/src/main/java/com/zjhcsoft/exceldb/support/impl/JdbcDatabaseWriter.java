package com.zjhcsoft.exceldb.support.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.zjhcsoft.exceldb.support.IDatabaseWriter;
import com.zjhcsoft.exceldb.support.IExcelDao;

/**
 * 
 * @author liuxin 2011-4-12
 * 
 */
public class JdbcDatabaseWriter implements IDatabaseWriter {
	private IExcelDao excelDao;

	@Override
	public void write(Map<String, Object> map, String tableName) {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		sql.append("insert into " + tableName + "(");
		Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			sql.append(entry.getKey());
			if (entry.getValue() instanceof String) {
				sql2.append("'" + entry.getValue() + "'");
			} else {
				sql2.append(entry.getValue());
			}
			if (iterator.hasNext()) {
				sql.append(",");
				sql2.append(",");
			}
		}
		sql.append(")values(");
		sql.append(sql2 + ")");
		excelDao.create(sql.toString(), null);
	}

	public void setExcelDao(IExcelDao excelDao) {
		this.excelDao = excelDao;
	}

	public IExcelDao getExcelDao() {
		return excelDao;
	}

}
