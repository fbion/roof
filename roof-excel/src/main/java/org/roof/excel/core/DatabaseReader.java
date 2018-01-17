package org.roof.excel.core;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 数据库读取
 * 
 * @author liuxin 2011-4-12
 * 
 */
public interface DatabaseReader extends Iterator<List<Map<String, Object>>> {

	public void init();
}
