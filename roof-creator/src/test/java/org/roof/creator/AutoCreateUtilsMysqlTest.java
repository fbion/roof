package org.roof.creator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class AutoCreateUtilsMysqlTest extends AbstractJUnit4SpringContextTests {
	AutoCreateUtilsMysql autoCreateUtils;

	// private SqlSessionTemplate sqlSessionTemplate;

	@Test
	public void testCreateCode() {

		String packagePath = "org.roof.web.org";

		List<String> sourcelist = new ArrayList<String>();

		sourcelist.add("s_organization");// 添加需要生成的表名

		// t_id
		autoCreateUtils.createCode(packagePath, sourcelist, "", true);

	}

	@Test
	public void testSql() {

	}

	@Autowired(required = true)
	public void setAutoCreateUtils(@Qualifier("autoCreateUtilsMysql") AutoCreateUtilsMysql autoCreateUtils) {
		this.autoCreateUtils = autoCreateUtils;
	}

	// @Resource(name = "sqlSessionTemplate")
	// public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate)
	// {
	// this.sqlSessionTemplate = sqlSessionTemplate;
	// }

}
