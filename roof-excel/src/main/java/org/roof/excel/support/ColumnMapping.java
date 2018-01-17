package org.roof.excel.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roof.excel.config.Column;

public class ColumnMapping {

	private List<Column> columns;
	private Map<String, String> db2xsl;
	private Map<String, String> xsl2db;
	private Map<String, String> xsl2title;

	public String[] getXslColumns() {
		return (String[]) xsl2db.keySet().toArray(new String[] {});
	}

	public ColumnMapping(List<Column> columns) {
		super();
		this.columns = columns;
		init();
	}

	private void init() {
		db2xsl = new HashMap<String, String>();
		xsl2db = new HashMap<String, String>();
		xsl2title = new HashMap<String, String>();
		for (Column column : columns) {
			db2xsl.put(column.getDbcol(), column.getXslcol());
			xsl2db.put(column.getXslcol(), column.getDbcol());
			xsl2title.put(column.getXslcol(), column.getTitle());
		}
	}

	public String getXslColumn(String dbColumn) {
		return db2xsl.get(dbColumn);
	}

	public String getDbColumn(String xslColumn) {
		return xsl2db.get(xslColumn);
	}

	public String getTitle(String xslColumn) {
		return xsl2title.get(xslColumn);
	}

}
