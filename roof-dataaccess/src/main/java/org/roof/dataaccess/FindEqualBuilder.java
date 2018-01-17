package org.roof.dataaccess;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;

/**
 * 自定义约束构建<br />
 * 根据值对象中的非空属性作为条件进行查询<br/>
 * ignores 为忽略的查询属性<br />
 * mapping 为实体类属性(key)和值对象属性(value)映射
 * 
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 Constraints.java 2012-7-27
 */
public class FindEqualBuilder {

	/**
	 * 实体类
	 */
	private Class<?> entityClass;
	/**
	 * 值对象
	 */
	private Object paramObj;
	/**
	 * 实体类中忽略查询的属性
	 */
	private String[] ignores;
	/**
	 * 实体类属性和值对象属性映射
	 */
	private Map<String, String> mapping;
	/**
	 * 添加的自定义查询条件
	 */
	private Collection<Criterion> criterions;
	/**
	 * 默认生成的查询
	 */
	private Collection<Criterion> result;
	/**
	 * 排序条件
	 */
	private Order[] orders;

	private static final Logger logger = Logger
			.getLogger(FindEqualBuilder.class);

	private FindEqualBuilder() {
	}

	public static FindEqualBuilder getInstance() {
		return new FindEqualBuilder();
	}

	/**
	 * 初始化方法
	 */

	public void init() {
		Assert.notNull(paramObj);
		if (entityClass == null) {
			entityClass = paramObj.getClass();
		}
		result = new ArrayList<Criterion>();
		try {
			PropertyDescriptor[] propertyDescriptors = PropertyUtils
					.getPropertyDescriptors(paramObj.getClass());
			for (PropertyDescriptor pd : propertyDescriptors) {
				if (StringUtils.equals(pd.getName(), "class")) {
					continue;
				}
				String propName = findPropertyInMapping(pd.getName());
				if (!isInIgnore(propName)) {
					Object value = pd.getReadMethod().invoke(paramObj,
							ArrayUtils.EMPTY_OBJECT_ARRAY);
					if (value != null) {
						result.add(Restrictions.eq(propName, value));
					}
				}
			}
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (InvocationTargetException e) {
			logger.error(e);
		}
		if (criterions != null) {
			result.addAll(criterions);
		}
	}

	/**
	 * 获得查询条件
	 * 
	 * @return
	 */

	public Collection<Criterion> get() {
		Collection<Criterion> collection = new ArrayList<Criterion>();
		if (result != null) {
			collection.addAll(result);
		}
		if (criterions != null) {
			collection.addAll(criterions);
		}
		return collection;
	}

	private String findPropertyInMapping(String valProperty) {
		if (MapUtils.isEmpty(mapping)) {
			return valProperty;
		}
		for (Entry<String, String> entry : mapping.entrySet()) {
			if (StringUtils.equals(valProperty, entry.getValue())) {
				return entry.getKey();
			}
		}
		return valProperty;
	}

	public FindEqualBuilder addOrder(Order order) {
		orders = ArrayUtils.add(orders, order);
		return this;
	}

	/**
	 * 添加忽略的实体类属性
	 * 
	 * @param propertyName
	 *            实体类属性
	 * @return
	 */

	public FindEqualBuilder addIgnores(String propertyName) {
		ignores = ArrayUtils.add(ignores, propertyName);
		return this;
	}

	/**
	 * 添加实体类与值对象属性的映射
	 * 
	 * @param propertyName
	 *            实体属性
	 * @param paramPropertyName
	 *            值对象名称
	 * @return
	 */

	public FindEqualBuilder addMapping(String propertyName,
			String paramPropertyName) {
		if (mapping == null) {
			mapping = new HashMap<String, String>();
		}
		mapping.put(propertyName, paramPropertyName);
		return this;
	}

	/**
	 * 添加其他条件
	 * 
	 * @see {@link org.hibernate.criterion.Restrictions}
	 * @param criterion
	 * @return
	 */

	public FindEqualBuilder addCriterion(Criterion criterion) {
		if (criterions == null) {
			criterions = new ArrayList<Criterion>();
		}
		criterions.add(criterion);
		return this;
	}

	private boolean isInIgnore(String propertyName) {
		if (ArrayUtils.isEmpty(ignores)) {
			return false;
		}
		for (String ignore : ignores) {
			if (StringUtils.equals(ignore, propertyName)) {
				return true;
			}
		}
		return false;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public Object getParamObj() {
		return paramObj;
	}

	public String[] getIgnores() {
		return ignores;
	}

	public Map<String, String> getMapping() {
		return mapping;
	}

	public Collection<Criterion> getCriterions() {
		return criterions;
	}

	public FindEqualBuilder setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
		return this;
	}

	public FindEqualBuilder setParamObj(Object paramObj) {
		this.paramObj = paramObj;
		return this;
	}

	public FindEqualBuilder setIgnores(String[] ignores) {
		this.ignores = ignores;
		return this;
	}

	public FindEqualBuilder setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
		return this;
	}

	public FindEqualBuilder setCriterions(Collection<Criterion> criterions) {
		this.criterions = criterions;
		return this;
	}

	public Order[] getOrders() {
		return orders;
	}

	public FindEqualBuilder setOrders(Order[] orders) {
		this.orders = orders;
		return this;
	}

	public Collection<Criterion> getResult() {
		return result;
	}

	public FindEqualBuilder setResult(Collection<Criterion> result) {
		this.result = result;
		return this;
	}

}
