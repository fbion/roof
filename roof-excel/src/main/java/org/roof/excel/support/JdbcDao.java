package org.roof.excel.support;

import java.util.List;
import java.util.Map;

import org.roof.excel.core.ExcelDao;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.sql.PagerUtils;

/**
 * Title: exceldb2<br>
 * Description: <br>
 * Create DateTime: 2010-10-26 下午02:42:24 <br>
 * 
 * @author liuxin
 */
public class JdbcDao implements ExcelDao {
	protected JdbcTemplate jdbcTemplate;

	private String dbType;

	public static final String ROWNUM = "RN";

	@Override
	public void insert(String sql, Object... args) {
		jdbcTemplate.update(sql, args);
	}

	@Override
	public List<Map<String, Object>> select(String sql, int offset, int count, Object... args) {
		String result = PagerUtils.limit(sql, dbType, offset, count);
		return jdbcTemplate.query(result, args, new ColumnMapRowMapper());
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int count(String sql, Object... args) {
		String result = PagerUtils.count(sql, dbType);
		return jdbcTemplate.queryForObject(result, Integer.class, args);
	}

}
