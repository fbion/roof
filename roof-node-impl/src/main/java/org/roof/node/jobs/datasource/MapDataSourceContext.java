package org.roof.node.jobs.datasource;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;

public class MapDataSourceContext implements DataSourceContext, DisposableBean {
	private static final ConcurrentMap<String, Object> DATASOURCE_MAP = new ConcurrentHashMap<>();
	private Map<String, String> destroyMethod;

	private static final Logger LOGGER = Logger.getLogger(MapDataSourceContext.class);

	@Override
	public Object get(String dataSourceName) {
		return DATASOURCE_MAP.get(dataSourceName);
	}

	@Override
	public void put(String dataSourceName, Object dataSource) {
		DATASOURCE_MAP.put(dataSourceName, dataSource);
	}

	@Override
	public void remove(String dataSourceName) {
		Object dataSource = get(dataSourceName);
		destroy(dataSource);
		DATASOURCE_MAP.remove(dataSourceName);
	}

	@Override
	public Map<String, Object> getAll() {
		return DATASOURCE_MAP;
	}

	@Override
	public void destroy() throws Exception {
		for (Entry<String, Object> entry : DATASOURCE_MAP.entrySet()) {
			this.destroy(entry.getValue());
		}
	}

	private void destroy(Object dataSource) {
		String className = dataSource.getClass().getName();
		String destroyMethodName = destroyMethod.get(className);
		try {
			MethodUtils.invokeMethod(dataSource, destroyMethodName, ArrayUtils.EMPTY_OBJECT_ARRAY);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public Map<String, String> getDestroyMethod() {
		return destroyMethod;
	}

	public void setDestroyMethod(Map<String, String> destroyMethod) {
		this.destroyMethod = destroyMethod;
	}

}
