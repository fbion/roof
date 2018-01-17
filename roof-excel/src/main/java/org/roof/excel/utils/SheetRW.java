package org.roof.excel.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.util.Assert;

public class SheetRW {

	private Sheet sheet;
	private Logger logger = Logger.getLogger(this.getClass());
	private Row currentRow; // 当前行

	public SheetRW(Sheet sheet) {
		this.sheet = sheet;
	}

	public void setCurrentRow(int row) {
		currentRow = sheet.getRow(row);
		if (currentRow == null) {
			currentRow = sheet.createRow(row);
		}
	}

	public Object read(int col) {
		Cell cell = currentRow.getCell(col);
		return readCellVal(cell);
	}

	public Object read(int row, int col) {
		Assert.notNull(sheet, "sheet 不能为空!");
		Row hrow = sheet.getRow(row);
		if (hrow == null) {
			if (logger.isInfoEnabled()) {
				logger.info("找不到第" + row + "行");
			}
			return null;
		}
		Cell cell = hrow.getCell(col);
		if (cell == null) {
			if (logger.isInfoEnabled()) {
				logger.info("找不到第" + col + "列");
			}
			return null;
		}
		return readCellVal(cell);
	}

	private Object readCellVal(Cell cell) {
		Object o = null;
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			// 得到Boolean对象的方法
			o = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			// 先看是否是日期格式
			if (DateUtil.isCellDateFormatted(cell)) {
				// 读取日期格式
				o = cell.getDateCellValue();
			} else {
				// 读取数字
				o = cell.getNumericCellValue();
			}
			break;
		case Cell.CELL_TYPE_FORMULA:
			// 读取公式
			o = cell.getCellFormula();
			break;
		case Cell.CELL_TYPE_STRING:
			// 读取String
			o = cell.getRichStringCellValue().toString();
			break;
		}
		return o;
	}

	public Object read(int row, String col) {
		return this.read(row, ColumnHelp.change(col));
	}

	public void write(int row, int col, Object data) {
		Assert.notNull(sheet, "sheet 不能为空!");

		Row hssfRow = sheet.getRow(row);
		if (hssfRow == null) {
			hssfRow = sheet.createRow(row);
		}
		Cell cell = hssfRow.getCell(col);
		if (cell == null) {
			cell = hssfRow.createCell(col);
		}

		writeCellVal(data, cell);
		if (logger.isDebugEnabled()) {
			logger.debug(row + "行," + col + "列插入数据:" + data);
		}

	}

	public void write(int col, Object data) {
		Cell cell = currentRow.getCell(col);
		if (cell == null) {
			cell = currentRow.createCell(col);
		}
		writeCellVal(data, cell);
	}

	private void writeCellVal(Object data, Cell cell) {
		if (data != null) {
			if (data instanceof String) {
				cell.setCellValue((String) data);
			} else if (data instanceof BigDecimal) {
				BigDecimal bigDecimal = (BigDecimal) data;
				cell.setCellValue(bigDecimal.doubleValue());
			} else if (data instanceof Calendar) {
				cell.setCellValue((Calendar) data);
			} else if (data instanceof java.sql.Date) {
				String d = changeDate((java.sql.Date) data);
				cell.setCellValue(d);
			} else if (data instanceof Timestamp) {
				String d = changeDate((Timestamp) data);
				cell.setCellValue(d);
			} else if (data instanceof Boolean) {
				cell.setCellValue((Boolean) data);
			} else {
				cell.setCellValue(data.toString());
			}
		}
	}

	/**
	 * 指定行和列插入数据
	 * 
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @param data
	 *            数据
	 * 
	 */
	public void write(int row, String col, Object data) {
		int c = ColumnHelp.change(col);
		this.write(row, c, data);
	}

	private String changeDate(java.util.Date date) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(date);
	}

	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

}
