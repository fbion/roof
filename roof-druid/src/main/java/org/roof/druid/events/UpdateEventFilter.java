package org.roof.druid.events;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLNullExpr;
import com.alibaba.druid.sql.ast.expr.SQLNumberExpr;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement.ValuesClause;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlDeleteStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitorAdapter;
import com.alibaba.druid.util.JdbcUtils;

/**
 * 数据更新事件过滤器
 * <p>
 * 把数据更新事件写入一个 {@link MessageChannel}
 * </p>
 * 
 * @author liuxin
 *
 */
public class UpdateEventFilter extends FilterEventAdapter {

	private MessageChannel messageChannel; // 事件写出通道
	private static final String TABLE_NAME = "tableName";
	private static final String ID_PROPERTY = "idProperty";
	private static final String DEFAULT_ID_PROPERTY = "id";
	/**
	 * 需要同步的表描述
	 * 
	 * tableName : 表名 <br />
	 * idProperty : id字段名称
	 * 
	 */
	private Map<String, String>[] includeTables;

	private static final Logger LOG = Logger.getLogger(UpdateEventFilter.class);

	@Override
	protected void statementExecuteAfter(StatementProxy statement, String sql, boolean result) {
		super.statementExecuteAfter(statement, sql, result);
		if (StringUtils.startsWithIgnoreCase(sql, "select"))
			return;

		Object id = null;
		String formatedSql = formatSql(statement, sql);
		String tableName = getTableName(formatedSql);
		if (containsTable(tableName) == null) {
			return;
		}
		List<Object> jdbcParameters = getParameters(statement);
		if (StringUtils.startsWithIgnoreCase(sql, "insert")) {
			id = getInsertId(statement);
			SQLStatement mySqlStatement = parseStatements(formatedSql);
			ValuesClause valuesClause = null;
			if (mySqlStatement instanceof SQLInsertStatement) {
				SQLInsertStatement insertStatement = ((SQLInsertStatement) mySqlStatement);
				valuesClause = insertStatement.getValues();
				List<SQLExpr> columns = insertStatement.getColumns();
				int index = getColumnIndex(tableName, columns);
				if (index != -1) {
					if (valuesClause.getValues().get(index) instanceof SQLNullExpr) {
						valuesClause.getValues().set(index, new SQLNumberExpr((Number) id));
					}
				} else {
					columns.add(new SQLIdentifierExpr(getIdProperty(tableName)));
					valuesClause.addValue(new SQLNumberExpr((Number) id));
				}
				StringBuffer b = new StringBuffer();
				insertStatement.accept(new MySqlOutputVisitor(b));
				formatedSql = b.toString();
			}

		} else {
			id = getUpdateId(formatedSql, tableName);
		}
		UpdateEvent updateEvent = new UpdateEvent(System.currentTimeMillis(), tableName, sql, formatedSql,
				jdbcParameters, id);
		updateEvent.setIdProperty(getIdProperty(tableName));
		if (LOG.isInfoEnabled()) {
			LOG.info("sql update event:" + updateEvent);
		}
		Message<UpdateEvent> message = new GenericMessage<UpdateEvent>(updateEvent);
		if (messageChannel != null) {
			messageChannel.send(message);
		}

	}

	// 查找主键所在的位置
	private int getColumnIndex(String tableName, List<SQLExpr> columns) {
		int index = -1;
		for (SQLExpr sqlExpr : columns) {
			index++;
			if (sqlExpr instanceof SQLIdentifierExpr) {
				SQLIdentifierExpr expr = (SQLIdentifierExpr) sqlExpr;
				if (StringUtils.equalsIgnoreCase(expr.getName(), getIdProperty(tableName))) {
					break;
				}
			}
		}
		return index;
	}

	// 是否包含表
	private Map<String, String> containsTable(String tableName) {
		for (Map<String, String> map : includeTables) {
			String t = map.get(TABLE_NAME);
			if (StringUtils.equalsIgnoreCase(t, tableName)) {
				return map;
			}
		}
		return null;
	}

	// 获取update 主键值
	private Object getUpdateId(String sql, String tableName) {
		SQLStatement sqlStatement = parseStatements(sql);
		SQLExpr where = null;
		if (sqlStatement instanceof MySqlUpdateStatement) {
			MySqlUpdateStatement mySqlUpdateStatement = (MySqlUpdateStatement) sqlStatement;
			where = mySqlUpdateStatement.getWhere();
		}
		if (sqlStatement instanceof MySqlDeleteStatement) {
			MySqlDeleteStatement deleteStatement = (MySqlDeleteStatement) sqlStatement;
			where = deleteStatement.getWhere();
		}
		SQLBinaryOpExprVistor vistor = new SQLBinaryOpExprVistor(tableName);
		where.accept(vistor);
		return vistor.getVal();
	}

	private String getIdProperty(String tableName) {
		Map<String, String> props = containsTable(tableName);
		return props.get(ID_PROPERTY) == null ? DEFAULT_ID_PROPERTY : props.get(ID_PROPERTY);
	}

	// 获取insert 主键值
	private Object getInsertId(StatementProxy statement) {
		Object result = null;
		try {
			ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()"); // 通过额外查询获取generatedKey
			if (rs.next()) {
				result = rs.getInt(1);
			} else {
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return result;
	}

	private SQLStatement parseStatements(String sql) {
		SQLStatement sqlStatement = null;
		List<SQLStatement> statementList = SQLUtils.parseStatements(sql, JdbcUtils.MYSQL);
		if (CollectionUtils.isNotEmpty(statementList) && statementList.size() == 1) {
			sqlStatement = statementList.get(0);
		}
		return sqlStatement;
	}

	// 获取sql操作的表名
	private String getTableName(String sql) {
		SQLStatement sqlStatement = parseStatements(sql);
		if (sqlStatement != null) {
			if (sqlStatement instanceof MySqlUpdateStatement) {
				MySqlUpdateStatement mySqlUpdateStatement = (MySqlUpdateStatement) sqlStatement;
				return mySqlUpdateStatement.getTableName().getSimpleName();
			}
			if (sqlStatement instanceof MySqlInsertStatement) {
				MySqlInsertStatement mySqlInsertStatement = (MySqlInsertStatement) sqlStatement;
				return mySqlInsertStatement.getTableName().getSimpleName();
			}
			if (sqlStatement instanceof MySqlDeleteStatement) {
				MySqlDeleteStatement mySqlDeleteStatement = (MySqlDeleteStatement) sqlStatement;
				return mySqlDeleteStatement.getTableName().getSimpleName();
			}
		}
		return null;

	}

	// 获取参数列表
	@SuppressWarnings("unchecked")
	private List<Object> getParameters(StatementProxy statement) {
		int parametersSize = statement.getParametersSize();
		if (parametersSize == 0) {
			return (List<Object>) CollectionUtils.EMPTY_COLLECTION;
		}

		List<Object> parameters = new ArrayList<Object>(parametersSize);
		for (int i = 0; i < parametersSize; ++i) {
			JdbcParameter jdbcParam = statement.getParameter(i);
			parameters.add(jdbcParam.getValue());
		}
		return parameters;
	}

	// 获取最终执行的sql
	private String formatSql(StatementProxy statement, String sql) {
		List<Object> jdbcParameters = getParameters(statement);
		int parametersSize = jdbcParameters.size();
		if (parametersSize == 0) {
			return sql;
		}
		List<Object> parameters = new ArrayList<Object>(parametersSize);
		for (Object jdbcParameter : jdbcParameters) {
			parameters.add(jdbcParameter);
		}
		String dbType = statement.getConnectionProxy().getDirectDataSource().getDbType();
		String formattedSql = SQLUtils.format(sql, dbType, parameters);
		return formattedSql;
	}

	public MessageChannel getMessageChannel() {
		return messageChannel;
	}

	public void setMessageChannel(MessageChannel messageChannel) {
		this.messageChannel = messageChannel;
	}

	public Map<String, String>[] getIncludeTables() {
		return includeTables;
	}

	public void setIncludeTables(Map<String, String>[] includeTables) {
		this.includeTables = includeTables;
	}

	class SQLBinaryOpExprVistor extends SQLASTVisitorAdapter {
		private String tableName;
		Object val = null;

		public SQLBinaryOpExprVistor(String tableName) {
			this.tableName = tableName;
		}

		@Override
		public boolean visit(SQLBinaryOpExpr x) {
			if (val != null) {
				return true;
			}
			StringBuffer buf = new StringBuffer();
			x.getLeft().output(buf);
			while (!StringUtils.equals(getIdProperty(tableName), buf.toString())) {
				SQLObject parent = x.getParent();
				if (parent != null && parent instanceof SQLBinaryOpExpr) {
					x = (SQLBinaryOpExpr) parent;
					buf = new StringBuffer();
					x.getLeft().output(buf);
				} else {
					return true;
				}
			}
			val = x.getRight().toString();
			return true;
		}

		public Object getVal() {
			return val;
		}

	}
}
