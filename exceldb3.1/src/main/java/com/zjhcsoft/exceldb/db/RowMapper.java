package com.zjhcsoft.exceldb.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author liuxin
 * 
 */
public interface RowMapper<T> {
	public T mapRow(ResultSet rs) throws SQLException;
}
