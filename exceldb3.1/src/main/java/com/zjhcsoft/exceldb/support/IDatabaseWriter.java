package com.zjhcsoft.exceldb.support;

import java.util.Map;

/**
 * 数据库写入
 * 
 * @author liuxin 2011-4-12
 * 
 */
public interface IDatabaseWriter {
	void write(Map<String, Object> map, String tableName);
}
