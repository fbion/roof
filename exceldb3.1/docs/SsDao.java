package com.ctzj.bms.ass.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.sevenstar.persistent.db.ibatis.IbatisDao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zjhcsoft.exceldb.entity.Column;
import com.zjhcsoft.exceldb.entity.XslDb;
import com.zjhcsoft.exceldb.support.AbstractIbatisDao;
import com.zjhcsoft.exceldb.support.IExcelDao;

/**
 * 
 * @author liuxin 2011-4-14
 * 
 */
public class SsDao extends AbstractIbatisDao {

	static {
		sqlMapper = IbatisDao.getDao().getSqlMap();
	}

	public List<Map<String, Object>> read(String sql, int firstResult, int rowCount, XslDb xslDb) {
		List<Map<String, Object>> queryList = null;
		List<Map<String, Object>> resList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lastrownum", firstResult + rowCount);
		map.put("firstrownum", firstResult);
		try {
			queryList = sqlMapper.queryForList(sql, map);
			resList = mapResult(xslDb, queryList);
		} catch (SQLException e) {
			logger.error(e);
		}
		return resList;
	}

	public List<Map<String, Object>> read(String sql, int firstResult, int rowCount, Object... args) {
		List<Map<String, Object>> resList = null;
		try {
			Map<String, Object> map = null;
			if (args == null || args.length < 1) {
				map = new HashMap<String, Object>();
			} else {
				if (args[0] == null) {
					map = new HashMap<String, Object>();
				} else {
					map = (Map<String, Object>) args[0];
				}
			}
			map.put("lastrownum", firstResult + rowCount);
			map.put("firstrownum", firstResult);
			resList = sqlMapper.queryForList(sql, map);
		} catch (SQLException e) {
			logger.error(e);
		}
		return resList;
	}
}
