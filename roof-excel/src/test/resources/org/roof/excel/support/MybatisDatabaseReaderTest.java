package org.roof.excel.support;

import org.junit.After;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })

public class MybatisDatabaseReaderTest extends AbstractJUnit4SpringContextTests {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNext() {
	}

}
