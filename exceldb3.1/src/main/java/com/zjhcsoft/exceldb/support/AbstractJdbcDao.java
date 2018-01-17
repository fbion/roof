package com.zjhcsoft.exceldb.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.zjhcsoft.exceldb.entity.Column;
import com.zjhcsoft.exceldb.entity.XslDb;

/**
 * Title: exceldb2<br>
 * Description: <br>
 * Create DateTime: 2010-10-26 下午02:42:24 <br>
 * 
 * @author liuxin
 */
public abstract class AbstractJdbcDao implements IExcelDao {

	private JdbcTemplate jdbcTemplate;

	public static final String ROWNUM = "ROWNUM_";

	@Override
	public void create(String sql, Map<String, Object> parameterMap) {
		System.out.println(sql);
		jdbcTemplate.execute(sql);
	}

	@Override
	public List<Map<String, Object>> read(String sql, int firstResult,
			int rowCount, final XslDb xslDb) {
		sql = createPageSql(sql, firstResult, rowCount);
		return read(sql, xslDb);
	}

	protected abstract String createPageSql(String sql, int firstResult,
			int rowCount);

	@Override
	public List<Map<String, Object>> read(String sql, final XslDb xslDb) {
		final List<Map<String, Object>> resVal = new ArrayList<Map<String, Object>>();
		jdbcTemplate.queryForObject(sql,
				new RowMapper<List<Map<String, Object>>>() {
					@Override
					public List<Map<String, Object>> mapRow(ResultSet rs,
							int rowNum) throws SQLException {
						do {
							Map<String, Object> map = new HashMap<String, Object>();
							Iterator<Column> iterator = xslDb.getColumns()
									.iterator();
							while (iterator.hasNext()) {
								Column column = iterator.next();
								Object o = rs.getObject(column.getDbcol());
								map.put(column.getXslcol(), o);
							}
							resVal.add(map);
						} while (rs.next());
						return resVal;
					}
				}, new Object[] {});
		return resVal;
	}

	@Override
	public int readColumn(String sql, Object... args) {
		return jdbcTemplate.queryForObject(sql, Integer.class, args);
	}

	@Override
	public List<Map<String, Object>> read(String sql, int firstResult,
			int rowCount, Object... args) {
		sql = createPageSql(sql, firstResult, rowCount);
		return read(sql, args);
	}

	@Override
	public List<Map<String, Object>> read(String sql, Object... args) {
		return jdbcTemplate.queryForList(sql, args);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
