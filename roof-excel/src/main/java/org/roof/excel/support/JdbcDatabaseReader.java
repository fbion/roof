package org.roof.excel.support;

import java.util.List;
import java.util.Map;

import org.roof.excel.core.DatabaseReader;
import org.roof.excel.core.ExcelDao;

/**
 * 
 * @author liuxin 2011-4-12
 * 
 */
public class JdbcDatabaseReader implements DatabaseReader {
	private int currentRowNum;
	private int rowCount;
	private ExcelDao excelDao;
	private int fetchSize = 10000;
	private String sql;
	private Object[] args;

	public JdbcDatabaseReader(ExcelDao excelDao, String sql, Object[] args) {
		super();
		this.excelDao = excelDao;
		this.sql = sql;
		this.args = args;
	}

	public void init() {
		currentRowNum = 0;
		rowCount = 0;
		initRowCount();
	}

	private void initRowCount() {
		rowCount = excelDao.count(sql, args);
	}

	@Override
	public boolean hasNext() {
		if (currentRowNum == 0) {
			init();
		}
		if (rowCount > currentRowNum) {
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> next() {
		List<Map<String, Object>> resVal = null;
		resVal = excelDao.select(sql, currentRowNum, rowCount, args);
		currentRowNum += fetchSize;
		return resVal;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("不支持 remove方法");
	}

}
