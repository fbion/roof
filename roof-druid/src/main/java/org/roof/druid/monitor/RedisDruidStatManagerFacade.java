package org.roof.druid.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.druid.pool.DruidDataSourceStatValue;
import com.alibaba.fastjson.JSON;

public class RedisDruidStatManagerFacade {
	private RedisTemplate<String, String> redisTemplate;

	private BoundSetOperations<String, String> boundSetOperations;

	public RedisDruidStatManagerFacade(RedisTemplate<String, String> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
		boundSetOperations = redisTemplate.boundSetOps(RedisDruidConstant.DRUID_DATASOURCESTAT_KEYS);
	}

	public List<DruidDataSourceStatValue> getDataSourceStatDataList() {
		return getDataSourceStatDataList(false);
	}

	public List<DruidDataSourceStatValue> getDataSourceStatDataList(boolean includeSqlList) {
		Set<String> datasourceStatKeys = boundSetOperations.members();
		List<DruidDataSourceStatValue> datasourceList = new ArrayList<DruidDataSourceStatValue>();
		for (String datasourceStatKey : datasourceStatKeys) {
			String dataSourceStatJson = redisTemplate.boundValueOps(datasourceStatKey).get();
			DruidDataSourceStatValue dataSourceStatValue = JSON.parseObject(dataSourceStatJson,
					DruidDataSourceStatValue.class);
			datasourceList.add(dataSourceStatValue);
		}
		return datasourceList;
	}

}
