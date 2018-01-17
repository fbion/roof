package org.roof.node.service;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.jdbc.BeanPropertySqlParameterSourceFactory;
import org.springframework.integration.jdbc.SqlParameterSourceFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.messaging.Message;

public class DataMessageHandler extends AbstractMessageHandler {
	private final NamedParameterJdbcOperations jdbcOperations;

	private volatile SqlParameterSourceFactory sqlParameterSourceFactory = new BeanPropertySqlParameterSourceFactory();
	private String insertSql;
	private String updateSql;
	private String initUpdateSql;

	private static final Logger LOG = Logger.getLogger(DataMessageHandler.class);

	public DataMessageHandler(DataSource dataSource) {
		this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	protected void handleMessageInternal(Message<?> message) throws Exception {
		int i = executeUpdateQuery(insertSql, message);
		if (LOG.isInfoEnabled()) {
			LOG.info("更新实时监控数据" + i + "条");
		}
		int updatecount = executeUpdateQuery(updateSql, message);
		if (updatecount == 0) {
			executeUpdateQuery(initUpdateSql, message);
		}

	}

	protected int executeUpdateQuery(String updateSql, Object obj) {
		SqlParameterSource updateParameterSource = new MapSqlParameterSource();
		if (this.sqlParameterSourceFactory != null) {
			updateParameterSource = this.sqlParameterSourceFactory.createParameterSource(obj);
		}
		return this.jdbcOperations.update(updateSql, updateParameterSource);
	}

	public String getInsertSql() {
		return insertSql;
	}

	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}

	public String getUpdateSql() {
		return updateSql;
	}

	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
	}

	public String getInitUpdateSql() {
		return initUpdateSql;
	}

	public void setInitUpdateSql(String initUpdateSql) {
		this.initUpdateSql = initUpdateSql;
	}
}
