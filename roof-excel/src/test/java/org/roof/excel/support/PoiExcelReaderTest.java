package org.roof.excel.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;

public class PoiExcelReaderTest {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNext() {
		File file = new File("E:/excel/xsl_ecell.xls");
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			PoiExcelReader poiExcelReader = new PoiExcelReader(in, new String[] { "A", "B", "C" });
			while (poiExcelReader.hasNext()) {
				System.out.println(poiExcelReader.next());
			}
		} catch (FileNotFoundException e) {
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
