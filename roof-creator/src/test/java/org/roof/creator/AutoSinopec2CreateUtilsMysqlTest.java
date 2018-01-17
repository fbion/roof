package org.roof.creator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class AutoSinopec2CreateUtilsMysqlTest extends AbstractJUnit4SpringContextTests {
	AutoSinopec2CreateUtilsMysql autoSinopec2CreateUtilsMysql;

	@Test
	public void testCreateCode() {
		// 包名
		String packagePath = "com.sinopec2.crm.dictionary";

		// 添加需要生成的表名
		List<String> sourcelist = new ArrayList<String>();
		sourcelist.add("s_dictionary");
		
		// 拆分键
		autoSinopec2CreateUtilsMysql.createCode(packagePath, sourcelist, "", false); // 不带拆分键，不带页签
		//autoLetvCreateUtils.createCode(packagePath, sourcelist, "", true); // 不带拆分键，带页签
		//autoLetvCreateUtils.createCode(packagePath, sourcelist, "username"); // 带拆分键（不填默认为false）
	}

	@Test
	public void testSql() {

	}

	@Autowired(required = true)
	public void setAutoSinopec2CreateUtilsMysql(@Qualifier("autoSinopec2CreateUtilsMysql") AutoSinopec2CreateUtilsMysql autoSinopec2CreateUtilsMysql) {
		this.autoSinopec2CreateUtilsMysql = autoSinopec2CreateUtilsMysql;
	}

}