package org.roof.excel.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;
import org.roof.excel.config.XslDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring-*.xml" })

public class PoiExcelMappingReaderTest extends AbstractJUnit4SpringContextTests {
	private XslDb xslDb;

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNext() {
		File file = new File("E:/excel/xsl_ecell.xls");
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			PoiExcelMappingReader excelMappingReader = new PoiExcelMappingReader(in, xslDb);
			while (excelMappingReader.hasNext()) {
				System.out.println(excelMappingReader.next());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Autowired
	public void setXslDb(@Qualifier("ecellXslDb") XslDb xslDb) {
		this.xslDb = xslDb;
	}

}
