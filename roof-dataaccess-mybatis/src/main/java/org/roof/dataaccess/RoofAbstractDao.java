package org.roof.dataaccess;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据源类，拥有MyBATIS模版，jdbc模版
 */
public abstract class RoofAbstractDao implements IDaoSupport {

	protected SqlSessionTemplate sqlSessionTemplate;

	protected JdbcTemplate jdbcTemplate;

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Resource(name = "jdbcTemplate")
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
