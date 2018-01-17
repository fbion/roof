package com.zjhcsoft.exceldb.support.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.Test;

import com.zjhcsoft.exceldb.entity.XslDb;
import com.zjhcsoft.exceldb.support.IDatabaseWriter;
import com.zjhcsoft.exceldb.support.IExcelReader;

/**
 * 
 * @author liuxin 2011-4-13
 * 
 */
public class PoiExcelReaderTest {
	@Test
	public void testWrite() throws IOException {
		// 1.读取excel文件
		File file = new File("E:\\excel\\wz.xls");
		InputStream is = null;
		// 2.读取配置文件
		XslDb xslDb = MappingLoader.getXslDb("WZ_PERMISSION");
		try {
			is = new FileInputStream(file);
			// 3.创建excelReader
			IExcelReader excelReader = new PoiExcelReader(is, xslDb);
			// 4.创建databaseWriter
			IDatabaseWriter databaseWriter = new JdbcDatabaseWriter();
			// 4.迭代读取excel的
			while (excelReader.hasNext()) {
				Map<String, Object> map = excelReader.next();
				// 在这里可以添加自己的业务逻辑
				 databaseWriter.write(map, "ASS_KPI_ACCEPT_BASE_DATA");
				System.out.println(map);
			}
		} finally {
			is.close();
		}
	}
}
