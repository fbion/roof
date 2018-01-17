package com.zjhcsoft.exceldb.support;

/**
 * 
 * @author liuxin 2011-4-14
 * 
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zjhcsoft.exceldb.entity.Column;
import com.zjhcsoft.exceldb.entity.XslDb;

public abstract class AbstractIbatisDao implements IExcelDao {

	protected static SqlMapClient sqlMapper;
	protected Logger logger = Logger.getLogger(this.getClass());

	public AbstractIbatisDao() {
		super();
	}

	@Override
	public void create(String statementName, Map<String, Object> parameterMap) {
		try {
			sqlMapper.insert(statementName, parameterMap);
		} catch (SQLException e) {
			logger.error(e);
		}
	}

	@Override
	public List<Map<String, Object>> read(String statementName, XslDb xslDb) {
		List<Map<String, Object>> queryList = null;
		List<Map<String, Object>> resList = null;
		try {
			queryList = sqlMapper.queryForList(statementName);
			if (queryList.size() == 0) {
				return queryList;
			}
			resList = mapResult(xslDb, queryList);
		} catch (SQLException e) {
			logger.error(e);
		}
		return resList;
	}

	protected List<Map<String, Object>> mapResult(XslDb xslDb,
			List<Map<String, Object>> queryList) {
		List<Map<String, Object>> resList;
		resList = new ArrayList<Map<String, Object>>();
		List<Column> columns = createSortColumn(xslDb.getColumns(),
				queryList.get(0));
		for (Map<String, Object> map : queryList) {
			Map<String, Object> resMap = new HashMap<String, Object>();
			Set<Entry<String, Object>> entries = map.entrySet();
			for (Entry<String, Object> entry : entries) {
				for (Column c : columns) {
					if (StringUtils.equalsIgnoreCase(c.getDbcol(),
							entry.getKey())) {
						resMap.put(c.getXslcol(), entry.getValue());
					}
				}
			}
			resList.add(resMap);
		}
		return resList;
	}

	protected List<Column> createSortColumn(List<Column> columns,
			Map<String, Object> map) {
		List<Column> result = new ArrayList<Column>();
		Set<Entry<String, Object>> entries = map.entrySet();
		for (Entry<String, Object> entry : entries) {
			Column column = findColumnByDbcol(columns, entry.getKey());
			if (column != null) {
				result.add(column);
			}
		}
		return result;
	}

	protected Column findColumnByDbcol(List<Column> columns, String dbCol) {
		for (Column column : columns) {
			if (dbCol.equalsIgnoreCase(column.getDbcol())) {
				return column;
			}
		}
		return null;
		// throw new RuntimeException(dbCol + "在配置文件中没有找到映射");
	}

	@Override
	public List<Map<String, Object>> read(String statementName, Object... args) {
		List<Map<String, Object>> resList = null;
		try {
			if (args == null || args.length < 1) {
				resList = sqlMapper.queryForList(statementName);
			} else {
				resList = sqlMapper.queryForList(statementName, args[0]);
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return resList;
	}

	@Override
	public int readColumn(String statementName, Object... args) {
		Object o = null;

		try {
			if (args == null || args.length < 1) {
				o = sqlMapper.queryForObject(statementName);
			} else {
				o = sqlMapper.queryForObject(statementName, args[0]);
			}
			if (o instanceof Long) {
				long l = (Long) o;
				return (int) l;
			}
			if (o instanceof Integer) {
				return (Integer) o;
			}
			if (o instanceof String) {
				return Integer.parseInt((String) o);
			}
		} catch (SQLException e) {
			logger.error(
					"无发创建 ibatis count 语句,请使用"
							+ "com.zjhcsoft.exceldb.support.DatabaseReader.setCountSql(String countSql)手动设置",
					e);
		}
		return 0;
	}

	public static SqlMapClient getSqlMapper() {
		return sqlMapper;
	}

	public static void setSqlMapper(SqlMapClient sqlMapper) {
		AbstractIbatisDao.sqlMapper = sqlMapper;
	}

}