package org.roof.creator.bean;

/**
 * 主外键关系
 */
public class Relation {

	/**
	 * 主键表名
	 */
	private String primaryTable;

	/**
	 * 主键列名
	 */
	private String primaryCol;

	/**
	 * 外键表名
	 */
	private String foreignTable;

	/**
	 * 外键列名
	 */
	private String foreignCol;

	/**
	 * 对应的别名
	 */
	private String alias;

	public String getForeignCol() {
		return foreignCol;
	}

	public void setForeignCol(String foreignCol) {
		this.foreignCol = foreignCol;
	}

	public String getForeignTable() {
		return foreignTable;
	}

	public void setForeignTable(String foreignTable) {
		this.foreignTable = foreignTable;
	}

	public String getPrimaryCol() {
		return primaryCol;
	}

	public void setPrimaryCol(String primaryCol) {
		this.primaryCol = primaryCol;
	}

	public String getPrimaryTable() {
		return primaryTable;
	}

	public void setPrimaryTable(String primaryTable) {
		this.primaryTable = primaryTable;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
