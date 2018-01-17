package com.zjhcsoft.exceldb.entity;

import java.util.List;

import com.zjhcsoft.xdm.annotation.Ignore;
import com.zjhcsoft.xdm.annotation.Node;
import com.zjhcsoft.xdm.annotation.NodeType;
import com.zjhcsoft.xdm.annotation.Xml;

@Xml(name = "dbxsl")
public class XslDb {
	private String id;
	// private String xslName;
	private String tableName;
	private int ignore;
	private String idGenerator;// sequence, uuid
	private String idGeneratorValue;
	private String idColumn;
	private List<Column> columns;
	private List<Integer> sheets;

	@Node(type = NodeType.ATTR, name = "id")
	public String getId() {
		return id;
	}

//	@Node(type = NodeType.ATTR, name = "xslName")
//	public String getXslName() {
//		return xslName;
//	}

	@Node(type = NodeType.ATTR, name = "tableName")
	public String getTableName() {
		return tableName;
	}

	@Node(type = NodeType.ATTR, name = "ignore")
	public int getIgnore() {
		return ignore;
	}

	@Node(type = NodeType.ATTR, name = "idGenerator")
	public String getIdGenerator() {
		return idGenerator;
	}

	@Node(type = NodeType.ATTR, name = "idGeneratorValue")
	public String getIdGeneratorValue() {
		return idGeneratorValue;
	}

	@Node(type = NodeType.ATTR, name = "idColumn")
	public String getIdColumn() {
		return idColumn;
	}

	@Node(name = "col", surround = false, classType = Column.class)
	public List<Column> getColumns() {
		return columns;
	}

	@Ignore
	public List<Integer> getSheets() {
		return sheets;
	}

	public void setId(String id) {
		this.id = id;
	}

	// public void setXslName(String xslName) {
	// this.xslName = xslName;
	// }

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

	public void setSheets(List<Integer> sheets) {
		this.sheets = sheets;
	}

}
