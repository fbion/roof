package org.roof.excel.support;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.roof.excel.core.ExcelWriter;
import org.roof.excel.utils.ColumnHelp;
import org.roof.excel.utils.SheetRW;
import org.springframework.util.Assert;

/**
 * 
 * @author liuxin 2011-4-12
 * 
 */
public class PoiExcelWriter implements ExcelWriter {

	protected HSSFWorkbook wb;
	protected HSSFSheet sheet;
	protected SheetRW sheetRW;

	protected OutputStream os;
	protected int count;
	protected Logger LOGGER = Logger.getLogger(this.getClass());

	public PoiExcelWriter() {
		wb = new HSSFWorkbook();
		sheet = wb.createSheet();
		sheetRW = new SheetRW(sheet);
	}

	public void write(List<Map<String, Object>> list) {
		if (list == null || list.size() == 0) {
			return;
		}

		if (count >= 50000) {
			sheet = wb.createSheet();
			sheetRW = new SheetRW(sheet);
			count = 0;
		}
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
			sheetRW.setCurrentRow(count + i);
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				writeCell(entry.getKey(), entry.getValue());
			}
		}
		count += list.size();
	}

	protected void writeCell(String col, Object val) {
		if (JdbcDao.ROWNUM.equals(col)) {
			return;
		}
		int s = ColumnHelp.change(col);
		if (s > 255) {
			return;
		}
		sheetRW.write(s, val);
	}

	public void flush() {
		Assert.notNull(os, "OutputStream 不能为空!");
		try {
			wb.write(os);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public OutputStream getOs() {
		return os;
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}

}
