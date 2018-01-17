package com.zjhcsoft.exceldb.utils;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * id生成策略
 * 
 * @author liuxin 2011-3-10
 * 
 */
public class IdGenerator {
	private JdbcTemplate jdbcTemplate;

	public Object getId(String idGenerator, String idGeneratorValue) {
		if (StringUtils.equalsIgnoreCase(idGenerator, "uuid")) {
			return getUUID();
		}
		if (StringUtils.equalsIgnoreCase(idGenerator, "sequence")) {
			return getSequence(idGeneratorValue);
		}
		return null;
	}

	public String getUUID() {
		return UUID.randomUUID().toString();
	}

	public int getSequence(String sequenceName) {
		if (StringUtils.isBlank(sequenceName)) {
			throw new IllegalArgumentException(sequenceName
					+ "can not be Blank!");
		}
		String sql = "select " + sequenceName + ".NEXTVAL from DUAL";
		return jdbcTemplate.queryForObject(sql, Integer.class, new Object[] {});
	}

}
