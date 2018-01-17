package org.roof.excel.support;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Test;
import org.roof.excel.core.ExcelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring-*.xml" })
public class MybatisDatabaseReaderTest extends AbstractJUnit4SpringContextTests {

	private ExcelDao excelDao;

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNext() {
		MybatisDatabaseReader mybatisDatabaseReader = new MybatisDatabaseReader(excelDao, "select_e_ecell_used_record",
				null);
		while (mybatisDatabaseReader.hasNext()) {
			List<Map<String, Object>> list = mybatisDatabaseReader.next();
			for (Map<String, Object> map : list) {
				System.out.println(map);
			}
		}
	}

	@Autowired
	public void setExcelDao(@Qualifier("mybatisDao") ExcelDao excelDao) {
		this.excelDao = excelDao;
	}

}
