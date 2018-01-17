package org.roof.excel.support;

import java.util.List;
import java.util.Map;

import org.roof.excel.core.DatabaseReader;
import org.roof.excel.core.ExcelDao;
import org.springframework.util.Assert;

/**
 * 
 * @author liuxin 2011-4-13
 * 
 */
public class MybatisDatabaseReader implements DatabaseReader {
	private int currentRowNum;
	private int rowCount;
	private ExcelDao excelDao;
	private int fetchSize = 10000;

	private String statementName;
	private Object args;

	public MybatisDatabaseReader(ExcelDao excelDao, String statementName, Object args) {
		super();
		this.excelDao = excelDao;
		this.statementName = statementName;
		this.args = args;
	}

	public void init() {
		currentRowNum = 0;
		rowCount = 0;
		initRowCount();
	}

	private void initRowCount() {
		rowCount = excelDao.count(statementName, args);
	}

	@Override
	public boolean hasNext() {
		if (rowCount == 0) {
			init();
		}
		if (rowCount > currentRowNum) {
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> next() {
		Assert.notNull(statementName);
		List<Map<String, Object>> resVal = null;
		resVal = excelDao.select(statementName, currentRowNum, rowCount, args);
		currentRowNum += fetchSize;
		return resVal;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("不支持 remove方法");
	}

	public int getCurrentRowNum() {
		return currentRowNum;
	}

	public int getRowCount() {
		return rowCount;
	}

	public ExcelDao getExcelDao() {
		return excelDao;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public String getStatementName() {
		return statementName;
	}

	public Object getArgs() {
		return args;
	}

	public void setCurrentRowNum(int currentRowNum) {
		this.currentRowNum = currentRowNum;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setExcelDao(ExcelDao excelDao) {
		this.excelDao = excelDao;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public void setStatementName(String statementName) {
		this.statementName = statementName;
	}

	public void setArgs(Object args) {
		this.args = args;
	}

}
