package org.roof.druid.events;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库更新事件
 * 
 * @author liuxin
 *
 */
/**
 * @author liuxin
 *
 */
public class UpdateEvent implements Serializable {

	private static final long serialVersionUID = -3503980975616959917L;

	private long executionTime; // 执行时间
	private String sql; // sql语句
	private String formatedSql; // 参数化sql
	private List<Object> jdbcParameters; // sql参数
	private String tableName;// 操作的表名
	private String idProperty; // 主键的字段名
	private Object id; // 插入的主键

	public UpdateEvent() {
	}

	public UpdateEvent(long executionTime, String tableName, String sql, String formatedSql,
			List<Object> jdbcParameters, Object id) {
		super();
		this.tableName = tableName;
		this.executionTime = executionTime;
		this.sql = sql;
		this.formatedSql = formatedSql;
		this.jdbcParameters = jdbcParameters;
		this.id = id;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getFormatedSql() {
		return formatedSql;
	}

	public void setFormatedSql(String formatedSql) {
		this.formatedSql = formatedSql;
	}

	public List<Object> getJdbcParameters() {
		return jdbcParameters;
	}

	public void setJdbcParameters(List<Object> jdbcParameters) {
		this.jdbcParameters = jdbcParameters;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(String idProperty) {
		this.idProperty = idProperty;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UpdateEvent [\nexecutionTime=" + executionTime + ",\nsql=" + sql + ",\nformatedSql=" + formatedSql
				+ ", \njdbcParameters=" + jdbcParameters + ",\ntableName=" + tableName + ",\nidProperty=" + idProperty
				+ ",\nid=" + id + "]";
	}

}
