package org.roof.creator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class AutoCreateUtilsTest extends AbstractJUnit4SpringContextTests {
	AutoCreateUtils autoCreateUtils;

	@Test
	public void testCreateCode() {
		String packagePath = "com.zjhcsoft.evaluation.masterplate";

		List<String> sourcelist = new ArrayList<String>();
		sourcelist.add("E_MASTERPLATE");// 添加需要生成的表名

		autoCreateUtils.createCode(packagePath, sourcelist);

	}

	@Autowired(required = true)
	public void setAutoCreateUtils(@Qualifier("autoCreateUtils") AutoCreateUtils autoCreateUtils) {
		this.autoCreateUtils = autoCreateUtils;
	}

}
