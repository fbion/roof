package org.roof.druid.handler;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.roof.druid.events.UpdateEvent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * 监听数据库更新事件,并将更新写入到数据库
 * 
 * @author liuxin
 *
 */
public class SynDataUpdateEventMessageHandler implements MessageHandler, InitializingBean {
	private DataSource[] dataSources;
	private JdbcTemplate[] JdbcTemplates;

	private static final Logger LOG = Logger.getLogger(SynDataUpdateEventMessageHandler.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		if (dataSources == null) {
			return;
		}
		if (JdbcTemplates == null) {
			JdbcTemplates = new JdbcTemplate[dataSources.length];
		}
		for (int i = 0; i < dataSources.length; i++) {
			DataSource dataSource = dataSources[i];
			JdbcTemplates[i] = new JdbcTemplate(dataSource, true);
		}

	}

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		UpdateEvent updateEvent = (UpdateEvent) message.getPayload();
		if (JdbcTemplates == null) {
			return;
		}
		for (JdbcTemplate jdbcTemplate : JdbcTemplates) {
			if (LOG.isInfoEnabled()) {
				LOG.info("sql sys to" + jdbcTemplate.getDataSource() + "[" + updateEvent.getFormatedSql() + "]");
			}
			jdbcTemplate.update(updateEvent.getFormatedSql());
		}
	}

	public DataSource[] getDataSources() {
		return dataSources;
	}

	public void setDataSources(DataSource[] dataSources) {
		this.dataSources = dataSources;
	}

	public JdbcTemplate[] getJdbcTemplates() {
		return JdbcTemplates;
	}

	public void setJdbcTemplates(JdbcTemplate[] jdbcTemplates) {
		JdbcTemplates = jdbcTemplates;
	}

}
