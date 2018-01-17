package org.roof.excel.support;

import java.io.InputStream;
import java.util.Map;

import org.roof.excel.config.XslDb;
import org.roof.excel.utils.ColumnHelp;

public class PoiExcelMappingReader extends PoiExcelReader {
	private ColumnMapping columnMapping;

	public PoiExcelMappingReader(InputStream is, XslDb xslDb) {
		columnMapping = new ColumnMapping(xslDb.getColumns());
		this.setIs(is);
		this.setColumns(columnMapping.getXslColumns());
		init();
		this.currentRowNum = xslDb.getIgnore();
	}
	public PoiExcelMappingReader(InputStream is, XslDb xslDb,String postfix) {
		columnMapping = new ColumnMapping(xslDb.getColumns());
		this.setIs(is);
		this.setColumns(columnMapping.getXslColumns());
		super.postfix = postfix;
		init();
		this.currentRowNum = xslDb.getIgnore();
	}
	

	public boolean hasNext() {
		return (currentRowNum + 1) <= lastRowNum ? true : false;
	}

	public Map<String, Object> next() {
		resVal.clear();
		Object o = null;
		sheetRW.setCurrentRow(currentRowNum);
		for (int i = 0; i < columns.length; i++) {
			String column = columns[i];
			o = sheetRW.read(ColumnHelp.change(column));
			resVal.put(columnMapping.getDbColumn(column), o);
		}
		currentRowNum += 1;
		return resVal;
	}

}
