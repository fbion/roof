package org.roof.node.jobs.datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.fastjson.JSON;


/**
 * 使用Json配置,注册JDBC数据源
 * 
 * @author liuxin
 *
 */
public class JsonJdbcDataSourceRegister implements DataSourceRegister, InitializingBean {
	private static final Map<String, Object> default_datasource_params = new HashMap<>();

	private javax.naming.spi.ObjectFactory dataSourceFactory;
	private DataSourceContext dataSourceContext;

	private static final Logger LOGGER = Logger.getLogger(JsonJdbcDataSourceRegister.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		default_datasource_params.put("maxActive", "1");
		default_datasource_params.put("initialSize", "1");
		default_datasource_params.put("minIdle", "1");
		default_datasource_params.put("timeBetweenEvictionRunsMillis", "60000");
		default_datasource_params.put("minEvictableIdleTimeMillis", "300000");
		default_datasource_params.put("validationQuery", "SELECT 'z'");
		default_datasource_params.put("testWhileIdle", "true");
		default_datasource_params.put("testOnBorrow", "false");
		default_datasource_params.put("testOnReturn", "false");
	}

	@Override
	public String regist(String config, String name) throws Exception {
		Map<String, Object> paramInstance = new HashMap<>(); // 创建空的执行参数列表
		paramInstance.putAll(default_datasource_params);// 将默认参数加入执行参数列表
		Map<String, Object> configMap = JSON.parseObject(config, Map.class); // 将json串转化成Map
		paramInstance.putAll(configMap); // 将本次执行的参数 覆盖入默认的执行参数列表
		Properties properties = new Properties();
		properties.putAll(paramInstance); // 将执行参数转换成Properties
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("开始创建JDBC数据源:" + paramInstance);
		}
		if (name == null) {
			if (paramInstance.get("name") == null) {
				name = ObjectUtils.toString(paramInstance.get("url"));
			} else {
				name = ObjectUtils.toString(paramInstance.get("name"));
			}
		}
		if (dataSourceContext.get(name) != null) {
			return name;
		}

		DataSource dataSource = ((DruidDataSourceFactory) dataSourceFactory).createDataSource(properties); // 创建数据源

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("数据源[" + name + "]创建成功");
		}
		dataSourceContext.put(name, dataSource); // 将数据源注册入数据源context,供数据读取job使用
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("数据源[" + name + "]注册成功");
		}
		return name;
	}

	public javax.naming.spi.ObjectFactory getDataSourceFactory() {
		return dataSourceFactory;
	}

	public void setDataSourceFactory(javax.naming.spi.ObjectFactory dataSourceFactory) {
		this.dataSourceFactory = dataSourceFactory;
	}

	public DataSourceContext getDataSourceContext() {
		return dataSourceContext;
	}

	public void setDataSourceContext(DataSourceContext dataSourceContext) {
		this.dataSourceContext = dataSourceContext;
	}

}