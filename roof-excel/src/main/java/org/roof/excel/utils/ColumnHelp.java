package org.roof.excel.utils;

import org.apache.commons.lang3.math.NumberUtils;

public class ColumnHelp {

	public static int change(String columnName) {
		int resVal = -1;
		try {
			resVal = NumberUtils.createInteger(columnName);
		} catch (Exception e) {
			columnName = columnName.trim().toUpperCase();
			for (int i = 0; i < columnName.length(); i++) {
				int b = (int) Math.pow(26, columnName.length() - 1 - i);
				resVal += (columnName.charAt(i) - 64) * b;
			}
		}
		return resVal;
	}
}
