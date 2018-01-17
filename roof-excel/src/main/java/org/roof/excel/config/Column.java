package org.roof.excel.config;

public class Column {

	private String dbcol;
	private String xslcol;
	private String title;

	public Column() {
	}

	public Column(String dbcol, String xslcol, String title) {
		super();
		this.dbcol = dbcol;
		this.xslcol = xslcol;
		this.title = title;
	}

	public String getDbcol() {
		return dbcol;
	}

	public String getXslcol() {
		return xslcol;
	}

	public String getTitle() {
		return title;
	}

	public void setDbcol(String dbcol) {
		this.dbcol = dbcol;
	}

	public void setXslcol(String xslcol) {
		this.xslcol = xslcol;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
