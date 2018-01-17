package com.zjhcsoft.exceldb.support.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.zjhcsoft.exceldb.entity.XslDb;
import com.zjhcsoft.exceldb.support.IExcelWriter;

/**
 * 
 * @author liuxin 2011-4-12
 * 
 */
public class PoiExcelWriterTest {

	@Test
	public void testWrite() throws IOException {
		// 1.创建要写入的Excel文件
		File file = new File("E:\\excel\\test.xls");
		if (!file.exists()) {
			file.createNewFile();
		}
		// 2.读取配置文件
		XslDb xslDb = MappingLoader.getXslDb("ASS_KPI_ACCEPT_BASE_DATA");
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			// 3.创建excelWriter
			PoiExcelWriter poiExcelWriter = new PoiExcelWriter(os);
			// 4.创建databaseReader
			JdbcDatabaseReader databaseReader = new JdbcDatabaseReader();
			// 5.设置配置文件(可选操作)
			databaseReader.setXslDb(xslDb);
			// 6.迭代数据库读取数据
			while (databaseReader.hasNext()) {
				// 7.写入excel
				poiExcelWriter.write(databaseReader.next());
			}
			// 8.将excel写出到文件
			poiExcelWriter.flush();
		} finally {
			os.close();
		}
	}

	@Test
	public void testRead2() throws IOException {
		File file = new File("E:\\excel\\test2.xls");
		if (!file.exists()) {
			file.createNewFile();
		}
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			JdbcDatabaseReader databaseReader = new JdbcDatabaseReader();
			PoiExcelWriter poiExcelWriter = new PoiExcelWriter(os);
			databaseReader
					.setQuerySql("select * from ASS_KPI_ACCEPT_BASE_DATA");
			while (databaseReader.hasNext()) {
				poiExcelWriter.write(databaseReader.next());
			}
			poiExcelWriter.flush();
		} finally {
			os.close();
		}
	}
}
