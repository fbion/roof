package com.zjhcsoft.exceldb.support.impl;

import java.util.List;
import java.util.Map;

import com.zjhcsoft.exceldb.entity.XslDb;
import com.zjhcsoft.exceldb.support.IDatabaseReader;
import com.zjhcsoft.exceldb.support.IExcelDao;

/**
 * 
 * @author liuxin 2011-4-13
 * 
 */
public class IbatisDatabaseReader implements IDatabaseReader {
	private int currentRowNum;
	private int rowCount;
	private IExcelDao excelDao;
	private final int fetchSize;

	private XslDb xslDb;
	private String querySql;
	private String countSql;
	private Object[] args;

	public IbatisDatabaseReader(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	private void init() {
		initRowCount();
	}

	private void initRowCount() {
		rowCount = excelDao.readColumn(countSql.toString(), args);
		// if (rowCount > 60000) {
		// rowCount = 60000;
		// }
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
		List<Map<String, Object>> resVal = null;
		if (xslDb == null) {
			resVal = excelDao.read(querySql, currentRowNum, fetchSize, args);
		} else {
			resVal = excelDao.read(querySql, currentRowNum, fetchSize, xslDb,
					args);
		}
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

	public IExcelDao getExcelDao() {
		return excelDao;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public XslDb getXslDb() {
		return xslDb;
	}

	public String getQuerySql() {
		return querySql;
	}

	public String getCountSql() {
		return countSql;
	}

	public void setCurrentRowNum(int currentRowNum) {
		this.currentRowNum = currentRowNum;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setExcelDao(IExcelDao excelDao) {
		this.excelDao = excelDao;
	}

	public void setXslDb(XslDb xslDb) {
		this.xslDb = xslDb;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

}
