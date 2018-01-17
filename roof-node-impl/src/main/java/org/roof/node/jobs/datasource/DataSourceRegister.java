package org.roof.node.jobs.datasource;

/**
 * 注册数据源
 * 
 * @author liuxin
 *
 */
public interface DataSourceRegister {
	/**
	 * 注册数据源
	 * 
	 * @param config
	 *            数据源配置
	 * @param name
	 * @throws Exception
	 */
	String regist(String config, String name) throws Exception;
}
