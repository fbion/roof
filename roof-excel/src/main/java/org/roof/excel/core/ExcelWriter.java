package org.roof.excel.core;

import java.util.List;
import java.util.Map;

/**
 * Excel 写入
 * 
 * @author liuxin 2011-4-12
 * 
 */
public interface ExcelWriter {
	void write(List<Map<String, Object>> list);
}
