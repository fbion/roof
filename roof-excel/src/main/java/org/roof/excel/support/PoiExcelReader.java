package org.roof.excel.support;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.roof.excel.config.ExcelCommon;
import org.roof.excel.core.ExcelReader;
import org.roof.excel.utils.ColumnHelp;
import org.roof.excel.utils.SheetRW;
import org.springframework.util.Assert;

/**
 * 
 * @author liuxin 2011-4-13
 * 
 */
public class PoiExcelReader implements ExcelReader {
	protected InputStream is;
	protected Sheet sheet;
	protected SheetRW sheetRW;

	protected int currentRowNum;
	protected int lastRowNum;
	protected final Logger logger = Logger.getLogger(this.getClass());
	protected Map<String, Object> resVal = new HashMap<String, Object>();
	protected String[] columns;
	protected String postfix;

	public PoiExcelReader() {
	}

	public PoiExcelReader(InputStream in, String[] columns, String postfix) {
		this.is = in;
		this.columns = columns;
		this.postfix = postfix;
		init();
	}

	/**
	 * 使用apache poi读取Excel
	 * 
	 * @param is
	 *            Excel输入流
	 * @param columns
	 *            需要读取的Excel列,如 A,B,C
	 * @throws Exception
	 *             文件读取失败时抛出
	 */
	public PoiExcelReader(InputStream is, String[] columns) {
		this.is = is;
		this.columns = columns;
		init();
	}

	protected void init() {
		Workbook wb = null;
		Assert.notNull(is, "InputStream 不能为空!");
		Assert.notNull(columns);
		try {
			if (StringUtils.isBlank(postfix)) {
				wb = new HSSFWorkbook(is);
			} else if (ExcelCommon.EXCEL_2003_POSTFIX.equals(postfix.toLowerCase())) {
				wb = new HSSFWorkbook(is);
			} else if (ExcelCommon.EXCEL_2010_POSTFIX.equals(postfix.toLowerCase())) {
				wb = new XSSFWorkbook(is);
			}
		} catch (Exception e) {
		}
		// TODO 为实现多工作表
		sheet = wb.getSheetAt(0);
		lastRowNum = sheet.getLastRowNum();
		sheetRW = new SheetRW(sheet);
	}

	public boolean hasNext() {
		return (currentRowNum + 1) <= lastRowNum ? true : false;
	}

	public Map<String, Object> next() {
		resVal.clear();
		currentRowNum += 1;
		Object o = null;
		sheetRW.setCurrentRow(currentRowNum);
		for (int i = 0; i < columns.length; i++) {
			String column = columns[i];
			o = sheetRW.read(ColumnHelp.change(column));
			resVal.put(column, o);
		}
		return resVal;
	}

	public void remove() {
		throw new UnsupportedOperationException("不支持 remove方法");
	}

	public int getCurrentRowNum() {
		return currentRowNum;
	}

	public int getLastRowNum() {
		return lastRowNum;
	}

	public InputStream getIs() {
		return is;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

}
