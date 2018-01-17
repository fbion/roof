package com.zjhcsoft.exceldb.support;

import java.util.List;
import java.util.Map;

import com.zjhcsoft.exceldb.entity.XslDb;

/**
 * Title: 数据库交互接口<br>
 * Description: <br>
 * Create DateTime: 2010-10-26 上午09:49:07 <br>
 * 
 * @author liuxin
 */
public interface IExcelDao {

	public void create(String sql, Map<String, Object> parameterMap);

	public List<Map<String, Object>> read(String sql, int firstResult,
			int rowCount, final XslDb xslDb);

	public List<Map<String, Object>> read(String sql, int firstResult,
			int rowCount, final XslDb xslDb, Object[] args);

	public List<Map<String, Object>> read(String sql, XslDb xslDb);

	public List<Map<String, Object>> read(String sql, int firstResult,
			int rowCount, Object... args);

	public List<Map<String, Object>> read(String sql, Object... args);

	public int readColumn(String sql, Object... args);

}
