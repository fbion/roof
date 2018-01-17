package org.roof.roof.dataaccess.api;

import java.io.Serializable;
import java.util.Arrays;

/**
 * SQL描述
 * 
 * @author liuxin 2011-9-15
 * @version 1.0 SqlDescription.java liuxin 2011-9-15
 */
public class SqlDescription implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8871685120436023859L;
	private String sql;
	private Object[] parameters;

	public SqlDescription(String sql, Object[] parameters) {
		super();
		this.sql = sql;
		this.parameters = parameters;
	}

	public String getSql() {
		return sql;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "SqlDescription [sql=" + sql + ", parameters=" + Arrays.toString(parameters) + "]";
	}

}
