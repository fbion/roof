package org.roof.druid.monitor;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.druid.pool.DruidDataSourceStatLoggerImpl;
import com.alibaba.druid.pool.DruidDataSourceStatValue;
import com.alibaba.druid.stat.JdbcSqlStatValue;
import com.alibaba.fastjson.JSON;

public class RedisDruidDataSourceStatLogger extends DruidDataSourceStatLoggerImpl {
	private static final String DRUID_DATASOURCESTAT_KEYS = RedisDruidConstant.DRUID_DATASOURCESTAT_KEYS;
	private static final String DRUID_DATASOURCESTAT = RedisDruidConstant.DRUID_DATASOURCESTAT;
	private static final Logger LOGGER = Logger.getLogger(RedisDruidDataSourceStatLogger.class);
	private String host = null;
	private String key = null;
	private RedisTemplate<String, String> redisTemplate;

	public RedisDruidDataSourceStatLogger(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
		InetAddress netAddress = getInetAddress();
		try {
			host = getRealIp();
		} catch (SocketException e) {
		}
		if (host == null) {
			host = getHostName(netAddress);
		}
		key = DRUID_DATASOURCESTAT + ":" + host;

	}

	@Override
	public void log(DruidDataSourceStatValue statValue) {
		String dataSourceStatKey = key + ":" + statValue.getName();
		String sqlListStatKey = dataSourceStatKey + ":" + "sqlList";
		BoundSetOperations<String, String> boundSetOperations = redisTemplate.boundSetOps(DRUID_DATASOURCESTAT_KEYS);
		if (!boundSetOperations.isMember(dataSourceStatKey)) {
			boundSetOperations.add(dataSourceStatKey);
		}
		BoundValueOperations<String, String> dataSourceStatValueOperations = redisTemplate
				.boundValueOps(dataSourceStatKey);
		BoundValueOperations<String, String> sqlListValueOperations = redisTemplate.boundValueOps(sqlListStatKey);

		List<JdbcSqlStatValue> sqlList = statValue.getSqlList();
		statValue.setSqlList(null);

		String dateSourceStatJson = JSON.toJSONString(statValue);
		statValue.setSqlList(sqlList);

		String sqlListJson = JSON.toJSONString(sqlList);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("发送数据源监控数据:" + (dateSourceStatJson.length() + sqlListJson.length()) / 1024 + "kb");
		}
		sqlListValueOperations.set(sqlListJson);
		dataSourceStatValueOperations.set(dateSourceStatJson);
	}

	public static String getRealIp() throws SocketException {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		while (netInterfaces.hasMoreElements()) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
					return ip.getHostAddress();
				}
			}
		}
		return localip;
	}

	public static InetAddress getInetAddress() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;

	}

	private static String getHostName(InetAddress netAddress) {
		if (null == netAddress) {
			return null;
		}
		String name = netAddress.getHostName(); // get the host address
		return name;
	}

}
