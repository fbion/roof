package org.roof.excel.support;

import org.roof.excel.core.DatabaseWriter;
import org.roof.excel.core.ExcelDao;

/**
 * 
 * @author liuxin 2011-4-12
 * 
 */
public class JdbcDatabaseWriter implements DatabaseWriter {
	private ExcelDao excelDao;

	public JdbcDatabaseWriter(ExcelDao excelDao) {
		super();
		this.excelDao = excelDao;
	}

	@Override
	public void write(String sql, Object... args) {
		excelDao.insert(sql, args);
	}

}
