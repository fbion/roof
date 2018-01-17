package org.roof.node.jobs.datasource;

import java.util.Map;

/**
 * 数据源
 * 
 * @author liuxin
 *
 */
public interface DataSourceContext {
	/**
	 * 获取数据源
	 * 
	 * @param dataSourceName
	 *            数据源名称
	 * @return
	 */
	Object get(String dataSourceName);

	/**
	 * 添加数据源
	 * 
	 * @param dataSource
	 *            数据源
	 */
	void put(String dataSourceName, Object dataSource);

	/**
	 * 删除数据源
	 * 
	 * @param dataSourceName
	 *            数据源名称
	 */
	void remove(String dataSourceName);

	Map<String, Object> getAll();
}
