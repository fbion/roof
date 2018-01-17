package com.zjhcsoft.exceldb.support.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zjhcsoft.exceldb.entity.Column;
import com.zjhcsoft.exceldb.entity.XslDb;
import com.zjhcsoft.exceldb.support.AbstractJdbcDao;
import com.zjhcsoft.exceldb.support.IExcelWriter;
import com.zjhcsoft.exceldb.utils.ColumnHelp;
import com.zjhcsoft.exceldb.utils.SheetRW;

/**
 * 
 * @author liuxin 2011-4-12
 * 
 */
public class PoiExcelWriter implements IExcelWriter {

	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private SheetRW sheetRW;

	private XslDb xslDb;
	private OutputStream os;
	private int count;

	private Logger logger = Logger.getLogger(this.getClass());

	public PoiExcelWriter() {
		wb = new HSSFWorkbook();
		sheet = wb.createSheet();
		sheetRW = new SheetRW(sheet);
	}

	public PoiExcelWriter(XslDb xslDb, OutputStream os) {
		this();
		this.xslDb = xslDb;
		this.os = os;
		init();
	}

	public PoiExcelWriter(OutputStream os) {
		this();
		this.os = os;
		init();
	}

	private void init() {
		if (os == null) {
			throw new IllegalArgumentException("OutputStream 不能为空!");
		}
	}

	public void write(List<Map<String, Object>> list) {
		if (list == null || list.size() == 0) {
			return;
		}
		if (count == 0) {
			createTitle(list);
		}
		if (count >= 50000) {
			sheet = wb.createSheet();
			sheetRW = new SheetRW(sheet);
			count = 0;
		}
		boolean mapped = this.isMapped(list.get(0));
		for (int i = 0; i < list.size(); i++) {
			int col = 0;
			Map<String, Object> map = list.get(i);
			Iterator<Entry<String, Object>> iterator = map.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				if (AbstractJdbcDao.ROWNUM.equals(entry.getKey())) {
					continue;
				}
				int s = 0;
				if (mapped) {
					s = ColumnHelp.change(entry.getKey());
					if (s > 255) {
						continue;
					}
				} else {
					s = col;
					col++;
				}
				sheetRW.write(count + i, s, entry.getValue());
			}
		}
		count += list.size();
	}

	public void flush() {
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createTitle(List<Map<String, Object>> list) {
		Map<String, Object> titleMap = new LinkedHashMap<String, Object>();
		if (xslDb != null) {
			List<Column> columns = xslDb.getColumns();
			for (Column column : columns) {
				titleMap.put(column.getXslcol(), column.getTitle());
			}
		} else {
			Map<String, Object> map = list.get(0);
			Set<Entry<String, Object>> entries = map.entrySet();
			for (Entry<String, Object> entry : entries) {
				titleMap.put(entry.getKey(), entry.getKey());
			}
		}
		list.add(0, titleMap);
	}

	private boolean isMapped(Map<String, Object> map) {
		Set<String> keySet = map.keySet();
		for (String s : keySet) {
			if (ColumnHelp.change(s) > 255 && !AbstractJdbcDao.ROWNUM.equals(s)) {
				return false;
			}
		}
		return true;
	}

}
