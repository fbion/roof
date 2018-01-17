package org.roof.druid;

import org.junit.After;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class RedisDruidStatServiceTest extends AbstractJUnit4SpringContextTests {
	private RedisTemplate<String, String> redisTemplate;

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testService() {
	}

}
