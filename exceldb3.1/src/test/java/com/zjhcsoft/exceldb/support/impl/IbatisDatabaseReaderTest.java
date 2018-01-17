package com.zjhcsoft.exceldb.support.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * 
 * @author liuxin 2011-4-13
 * 
 */
public class IbatisDatabaseReaderTest {
	// @Test
//	public void testRead() throws IOException {
//		OutputStream os = null;
//		try {
//			File file = new File("E:\\excel\\test5.xls");
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//			os = new FileOutputStream(file);
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("lastrownum", 65535);
//			map.put("firstrownum", 0);
//			IbatisDatabaseReader ibatisDatabaseReader = new IbatisDatabaseReader(
//					2000, "Ass_exp_select_score_user_page",
//					"Ass_exp_select_score_user_count", new Object[] { map });
//			List<Map<String, Object>> all = new ArrayList<Map<String, Object>>();
//			while (ibatisDatabaseReader.hasNext()) {
//				List<Map<String, Object>> list = ibatisDatabaseReader.next();
//				all.addAll(list);
//			}
//			PoiExcelWriter excelWriter = new PoiExcelWriter(os);
//			excelWriter.write(all);
//		} finally {
//			os.close();
//		}
//	}
}
