package com.zjhcsoft.exceldb.support.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.zjhcsoft.exceldb.entity.XslDb;
import com.zjhcsoft.exceldb.support.AbstractIbatisDao;

public class IbatisDao extends AbstractIbatisDao {

	/**
	 * ibatis自带分页效率不高
	 */
	@Override
	public List<Map<String, Object>> read(String statementName,
			int firstResult, int rowCount, XslDb xslDb) {
		List<Map<String, Object>> queryList = null;
		List<Map<String, Object>> resList = null;
		try {
			queryList = sqlMapper.queryForList(statementName, firstResult,
					rowCount);
			resList = mapResult(xslDb, queryList);
		} catch (SQLException e) {
			logger.error(e);
		}
		return resList;
	}

	@Override
	public List<Map<String, Object>> read(String statementName,
			int firstResult, int rowCount, XslDb xslDb, Object[] args) {

		List<Map<String, Object>> queryList = null;
		List<Map<String, Object>> resList = null;
		try {
			if (args == null || args.length < 1) {
				read(statementName, firstResult, rowCount, xslDb);
			} else {
				queryList = sqlMapper.queryForList(statementName, args[0],
						firstResult, rowCount);
				resList = mapResult(xslDb, queryList);
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return resList;
	}

	/**
	 * ibatis自带分页效率不高
	 */
	@Override
	public List<Map<String, Object>> read(String statementName,
			int firstResult, int rowCount, Object... args) {
		List<Map<String, Object>> resList = null;
		try {
			if (args == null || args.length < 1) {
				resList = sqlMapper.queryForList(statementName, firstResult,
						rowCount);
			} else {
				resList = sqlMapper.queryForList(statementName, args[0],
						firstResult, rowCount);
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return resList;
	}

}
