package org.roof.excel.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 
 * @author liuxin
 * 
 */
public class ExcelAutoMerger {

	private final HSSFSheet sheet;
	private final SheetRW sheetRW;
	private static final int MAX_COL_NUMBER = 255;
	private static final int MAX_ROW_NUMBER = 65535;

	public ExcelAutoMerger(HSSFSheet sheet) {
		super();
		this.sheet = sheet;
		sheetRW = new SheetRW(sheet);
	}

	/**
	 * 自动合并列
	 */
	public void doMergerRow(int maxCol) {
		Object val = null;
		Object val2 = null;
		int firstRow = 0;
		int lastRow = 0;
		if (maxCol > MAX_COL_NUMBER) {
			maxCol = MAX_COL_NUMBER;
		}
		for (int j = 0; j < maxCol; j++) {
			firstRow = 0;
			lastRow = 0;
			val = sheetRW.read(0, j);
			if (val == null) {
				return;
			}
			for (int i = 1; i < MAX_ROW_NUMBER; i++) {
				val2 = sheetRW.read(i, j);
				if (val == null && val2 == null && (lastRow - firstRow > 5)) {
					break;
				}
				if (ObjectUtils.equals(val, val2)) {
					lastRow = i;
				} else {
					merger(firstRow, lastRow, j, j);
					firstRow = i;
					lastRow = i;
					val = val2;
				}
			}
		}
	}

	private void merger(int firstRow, int lastRow, int firstCol, int lastCol) {
		CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow,
				lastRow, firstCol, lastCol);
		sheet.addMergedRegion(cellRangeAddress);
	}
}
