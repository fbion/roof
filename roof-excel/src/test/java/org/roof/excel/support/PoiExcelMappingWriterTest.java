package org.roof.excel.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Test;
import org.roof.excel.config.XslDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring-*.xml" })
public class PoiExcelMappingWriterTest extends AbstractJUnit4SpringContextTests {
	private XslDb xslDb;
	private MybatisDatabaseReader mybatisDatabaseReader;

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWrite() {
		File file = new File("E:/excel/xsl_ecell.xls");
		if (file.exists()) {
			file.delete();
		}
		mybatisDatabaseReader.setStatementName("select_e_ecell_used_record");
		FileOutputStream os = null;
		try {
			file.createNewFile();
			PoiExcelMappingWriter excelMappingWriter = new PoiExcelMappingWriter();
			os = new FileOutputStream(file);
			excelMappingWriter.setOs(os);
			excelMappingWriter.setXslDb(xslDb);
			while (mybatisDatabaseReader.hasNext()) {
				List<Map<String, Object>> list = mybatisDatabaseReader.next();
				excelMappingWriter.write(list);
			}
			excelMappingWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Autowired
	public void setXslDb(@Qualifier("ecellXslDb") XslDb xslDb) {
		this.xslDb = xslDb;
	}

	@Autowired
	public void setMybatisDatabaseReader(MybatisDatabaseReader mybatisDatabaseReader) {
		this.mybatisDatabaseReader = mybatisDatabaseReader;
	}

}
