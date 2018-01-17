package com.zjhcsoft.exceldb.support;

import java.util.List;
import java.util.Map;

/**
 * Excel 写入
 * 
 * @author liuxin 2011-4-12
 * 
 */
public interface IExcelWriter {
	void write(List<Map<String, Object>> list);
}
