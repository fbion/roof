package org.roof.excel.support;

import org.roof.excel.core.DatabaseWriter;
import org.roof.excel.core.ExcelDao;
import org.springframework.util.Assert;

/**
 * 
 * @author liuxin 2011-4-19
 * 
 */
public class MybatisDatabaseWriter implements DatabaseWriter {

	private ExcelDao excelDao;

	public MybatisDatabaseWriter(ExcelDao excelDao) {
		super();
		this.excelDao = excelDao;
	}

	@Override
	public void write(String statementName, Object... args) {
		Assert.notEmpty(args);
		excelDao.insert(statementName, args[0]);
	}

	public ExcelDao getExcelDao() {
		return excelDao;
	}

	public void setExcelDao(ExcelDao excelDao) {
		this.excelDao = excelDao;
	}

}
