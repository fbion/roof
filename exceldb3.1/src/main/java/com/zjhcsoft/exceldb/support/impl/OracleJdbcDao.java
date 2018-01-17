package com.zjhcsoft.exceldb.support.impl;

import java.util.List;
import java.util.Map;

import com.zjhcsoft.exceldb.entity.XslDb;
import com.zjhcsoft.exceldb.support.AbstractJdbcDao;

/**
 * 
 * @author liuxin 2011-4-12
 * 
 */
public class OracleJdbcDao extends AbstractJdbcDao {

	@Override
	protected String createPageSql(String sql, int firstResult, int rowCount) {
		int lastResult = rowCount + firstResult;
		sql = "SELECT * FROM (SELECT A.*, ROWNUM " + ROWNUM + " FROM (" + sql
				+ ") A WHERE ROWNUM <= " + lastResult + ")WHERE " + ROWNUM
				+ " > " + firstResult;
		return sql;
	}

	@Override
	public List<Map<String, Object>> read(String sql, int firstResult,
			int rowCount, XslDb xslDb, Object[] args) {
		throw new UnsupportedOperationException("不支持 read方法");
	}
}
