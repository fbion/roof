package com.zjhcsoft.exceldb.support.impl;

import java.util.Map;

import com.zjhcsoft.exceldb.support.IDatabaseWriter;
import com.zjhcsoft.exceldb.support.IExcelDao;

/**
 * 
 * @author liuxin 2011-4-19
 * 
 */
public class IbatisDatabaseWriter implements IDatabaseWriter {

	private IExcelDao excelDao;

	@Override
	public void write(Map<String, Object> parameterMap, String statementName) {
		excelDao.create(statementName, parameterMap);
	}

	public IExcelDao getExcelDao() {
		return excelDao;
	}

	public void setExcelDao(IExcelDao excelDao) {
		this.excelDao = excelDao;
	}

}
