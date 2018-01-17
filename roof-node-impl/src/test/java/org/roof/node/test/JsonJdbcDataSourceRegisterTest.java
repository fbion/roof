package org.roof.node.test;

import org.junit.Test;
import org.roof.node.jobs.datasource.DataSourceRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring-all.xml" })
public class JsonJdbcDataSourceRegisterTest extends AbstractJUnit4SpringContextTests {
	private DataSourceRegister jsonJdbcDataSourceRegister;

	@Test
	public void testRegist() throws Exception {
		String config = "{'url' : 'jdbc:mysql://10.80.8.244:3306/letv_gcr', 'username' : 'root', 'password' : '123'}";
		jsonJdbcDataSourceRegister.regist(config, null); // 注册数据源
	}

	@Autowired
	public void setJsonJdbcDataSourceRegister(
			@Qualifier("jsonJdbcDataSourceRegister") DataSourceRegister jsonJdbcDataSourceRegister) {
		this.jsonJdbcDataSourceRegister = jsonJdbcDataSourceRegister;
	}

}
