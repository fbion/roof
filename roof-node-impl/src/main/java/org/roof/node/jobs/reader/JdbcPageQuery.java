package org.roof.node.jobs.reader;

import java.util.List;

import org.apache.log4j.Logger;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.sql.PagerUtils;

public class JdbcPageQuery {

	protected JdbcTemplate jdbcTemplate;
	protected String dbType;
	
	private static final Logger LOG = Logger.getLogger(JdbcPageQuery.class);


	public JdbcPageQuery(JdbcTemplate jdbcTemplate, String dbType) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.dbType = dbType;
	}

	public Page select(String sql, Page page, Object[] args) {
		String queryStr = null;
		String countStr = null;
		try {
			queryStr = PagerUtils.limit(sql, dbType, page.getStart().intValue(), page.getLimit().intValue());
			countStr = PagerUtils.count(sql, dbType);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
		System.out.println("querysql:" + queryStr);
		System.out.println("querysql:" + countStr);
		Long rowCount = (Long) jdbcTemplate.queryForObject(countStr, args, Long.class);
		List<?> rows = jdbcTemplate.queryForList(queryStr, args);
		page.setTotal(rowCount);
		page.setDataList(rows);
		return page;
	}

	public void setDaoSupport(IDaoSupport daoSupport) {
		throw new UnsupportedOperationException();
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

}
