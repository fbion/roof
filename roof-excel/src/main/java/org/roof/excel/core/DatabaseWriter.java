package org.roof.excel.core;

/**
 * 数据库写入
 * 
 * @author liuxin 2011-4-12
 * 
 */
public interface DatabaseWriter {
	void write(String statementName, Object... args);
}
