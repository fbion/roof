package org.roof.excel.config;

import java.util.List;

public class XslDb {
	private int ignore;
	private String idGenerator;// sequence, uuid
	private String idGeneratorValue;
	private String idColumn;
	private List<Column> columns;

	public int getIgnore() {
		return ignore;
	}

	public String getIdGenerator() {
		return idGenerator;
	}

	public String getIdGeneratorValue() {
		return idGeneratorValue;
	}

	public String getIdColumn() {
		return idColumn;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setIgnore(int ignore) {
		this.ignore = ignore;
	}

	public void setIdGenerator(String idGenerator) {
		this.idGenerator = idGenerator;
	}

	public void setIdGeneratorValue(String idGeneratorValue) {
		this.idGeneratorValue = idGeneratorValue;
	}

	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

}
