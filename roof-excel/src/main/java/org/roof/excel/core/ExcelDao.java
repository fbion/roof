package org.roof.excel.core;

import java.util.List;
import java.util.Map;

/**
 * Title: 数据库交互接口<br>
 * Description: <br>
 * Create DateTime: 2010-10-26 上午09:49:07 <br>
 * 
 * @author liuxin
 */
public interface ExcelDao {

	public void insert(String sql, Object... args);

	public List<Map<String, Object>> select(String sql, int offset, int count, Object... args);

	public int count(String sql, Object... args);

}
