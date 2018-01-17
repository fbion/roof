package org.roof.excel.support;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionTemplate;
import org.roof.excel.core.ExcelDao;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.sql.PagerUtils;

public class MybatisDao implements ExcelDao {

	protected SqlSessionTemplate sqlSessionTemplate;
	protected JdbcTemplate jdbcTemplate;

	private String dbType;

	public SqlDescription getNamedSql(String statementName,
			Object parameterObject) {
		org.apache.ibatis.mapping.MappedStatement mappedStatement = sqlSessionTemplate
				.getSqlSessionFactory().getConfiguration()
				.getMappedStatement(statementName);
		TypeHandlerRegistry typeHandlerRegistry = mappedStatement
				.getConfiguration().getTypeHandlerRegistry();
		Configuration configuration = mappedStatement.getConfiguration();
		BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
		String hql = boundSql.getSql();
		if (parameterObject == null) {
			return new SqlDescription(hql, null);
		}

		List<ParameterMapping> parameterMappings = boundSql
				.getParameterMappings();
		Object[] parameters = new Object[parameterMappings.size()];
		if (parameterMappings != null) {
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry
							.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else {
						MetaObject metaObject = configuration
								.newMetaObject(parameterObject);
						value = metaObject.getValue(propertyName);
					}
					parameters[i] = value;
				}
			}
		}
		return new SqlDescription(hql, parameters);
	}

	@Override
	public void insert(String sql, Object... args) {
		validArgs(args);
		sqlSessionTemplate.insert(sql, args[0]);
	}

	private void validArgs(Object... args) {
		if (args == null || args.length != 1) {
			throw new IllegalArgumentException("参数必须为一个");
		}
	}

	@Override
	public List<Map<String, Object>> select(String sql, int offset, int count,
			Object... args) {
		validArgs(args);
		SqlDescription sqlDescription = getNamedSql(sql, args[0]);
		String result = PagerUtils.limit(sqlDescription.getSql(), dbType,
				offset, count);
		return jdbcTemplate.query(result, sqlDescription.getParameters(),
				new ColumnMapRowMapper());
	}

	@Override
	public int count(String sql, Object... args) {
		validArgs(args);
		SqlDescription sqlDescription = getNamedSql(sql, args[0]);
		String result = PagerUtils.count(sqlDescription.getSql(), dbType);
		return jdbcTemplate.queryForObject(result,
				sqlDescription.getParameters(), Integer.class);
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
