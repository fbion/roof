package org.roof.dataaccess;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.swing.SpringLayout.Constraints;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.collection.PersistentCollection;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.mybatis.spring.SqlSessionTemplate;
import org.roof.commons.RoofMapUtils;
import org.roof.roof.dataaccess.api.DaoException;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.SqlDescription;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 数据访问对象基类,提供了数据库访问的基础方法<br />
 * 如果这些方法都无法满足业务需求,可以考虑使用该类提供的 HibernateTemplate, SqlMapClientTemplate,
 * JdbcTemplate.
 * 
 * @author liuxin 2011-4-16
 * @version 2.0 RoofDaoSupport.java liuxin 2011-9-15
 */
@Transactional
public class RoofDaoSupport implements IDaoSupport {

	private static final Logger logger = Logger.getLogger(RoofDaoSupport.class);

	protected HibernateTemplate hibernateTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected JdbcTemplate jdbcTemplate;

	/**
	 * 重置lazy对象session
	 * 
	 * @param value
	 *            lazy的对象
	 */
	@Override
	public void resetSession(Object value) {
		Session session = hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		SessionImplementor sessionImplementor = null;
		if (session instanceof SessionImplementor) {
			sessionImplementor = (SessionImplementor) session;
		}
		if (sessionImplementor == null) {
			return;
		}
		if (value instanceof HibernateProxy) {// hibernate代理对象
			LazyInitializer initializer = ((HibernateProxy) value)
					.getHibernateLazyInitializer();
			if (initializer.getSession() == null
					|| initializer.getSession().isClosed()) {
				initializer.setSession(sessionImplementor);
			}
		} else if (value instanceof PersistentCollection) {// 实体关联集合一对多等
			PersistentCollection collection = (PersistentCollection) value;
			collection.setCurrentSession(sessionImplementor);
		}
	}

	/**
	 * 获取通用的数据库基本操作类
	 * 
	 * @return 容器中通用的基本操作类
	 */
	public static IDaoSupport getDao() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 获取XML文件中组装完成的SQL语句
	 * 
	 * @param statementName
	 *            查询语句 id
	 * @param parameterObject
	 *            参数对象
	 * @return 拼装后的查询语句和参数列表
	 */
	@Override
	public SqlDescription getNamedSql(String statementName,
			Object parameterObject) {
		org.apache.ibatis.mapping.MappedStatement mappedStatement = sqlSessionTemplate
				.getSqlSessionFactory().getConfiguration()
				.getMappedStatement(statementName);
		TypeHandlerRegistry typeHandlerRegistry = mappedStatement
				.getConfiguration().getTypeHandlerRegistry();
		Configuration configuration = mappedStatement.getConfiguration();
		BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
		String hql = boundSql.getSql();
		if (parameterObject == null) {
			return new SqlDescription(hql, null);
		}

		List<ParameterMapping> parameterMappings = boundSql
				.getParameterMappings();
		Object[] parameters = new Object[parameterMappings.size()];
		if (parameterMappings != null) {
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					if (boundSql.hasAdditionalParameter(propertyName)) { // issue
																			// #448
																			// ask
																			// first
																			// for
																			// additional
																			// params
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry
							.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else {
						MetaObject metaObject = configuration
								.newMetaObject(parameterObject);
						value = metaObject.getValue(propertyName);
					}
					parameters[i] = value;
				}
			}
		}
		return new SqlDescription(hql, parameters);
	}

	/**
	 * 持久化一个瞬时对象
	 * 
	 * @param entity
	 *            需要持久化的瞬时对象
	 * @return 生成的id
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Serializable save(Object entity) {
		return this.hibernateTemplate.save(entity);
	}

	/**
	 * 将一个持久化对象转化为瞬时对象
	 * 
	 * @param entity
	 *            需要持久化的瞬时对象
	 */
	@Override
	public void evict(Object entity) {
		hibernateTemplate.evict(entity);
	}

	/**
	 * 删除一个持久化对象
	 * 
	 * @param entity
	 *            需要删除的持久化对象
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Object entity) {
		this.hibernateTemplate.delete(entity);
	}

	/**
	 * 基于一个对象实例删除持久化对象
	 * 
	 * @param exampleEntity
	 *            对象实例
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteByExample(Object exampleEntity) {
		List<?> entities = this.findByExample(exampleEntity);
		this.hibernateTemplate.deleteAll(entities);
	}

	/**
	 * 根据ID延迟加载持久化对象
	 * 
	 * @param entityClass
	 *            类
	 * @param id
	 *            主键
	 * @return 加载的持久化对象
	 */
	@Override
	public <T> T load(Class<T> entityClass, Serializable id) {
		return this.load(entityClass, id, false);
	}

	/**
	 * 按锁模式查询数据
	 * 
	 * @param entityClass
	 * @param id
	 * @param lockMode
	 * @return
	 */
	public <T> T load(Class<T> entityClass, Serializable id, LockMode lockMode) {
		return this.hibernateTemplate.load(entityClass, id, lockMode);
	}

	/**
	 * 根据ID重新延迟加载持久化对象
	 * 
	 * @param entity
	 *            带有主键的实体类
	 * @return 加载的持久化对象
	 */
	@Override
	public Object reload(Object entity) {
		Assert.notNull(entity);
		Serializable id = getPrimaryKey(entity);
		Assert.notNull(id);
		return this.load(entity.getClass(), id);
	}

	/**
	 * 根据ID加载持久化对象
	 * 
	 * @param entityClass
	 *            类
	 * @param id
	 *            主键
	 * @param lazy
	 *            是否延迟加载
	 * @return 加载的持久化对象
	 */
	@Override
	public <T> T load(Class<T> entityClass, Serializable id, boolean lazy) {
		if (!lazy) {
			return this.hibernateTemplate.get(entityClass, id);
		}
		return this.hibernateTemplate.load(entityClass, id);
	}

	/**
	 * 加载所有的持久化对象
	 * 
	 * @param entityClass
	 *            类
	 * @return 加载的持久化对象
	 */
	@Override
	public <T> List<T> loadAll(Class<T> entityClass) {
		return this.hibernateTemplate.loadAll(entityClass);
	}

	/**
	 * 执行HQL查询语句
	 * 
	 * @param queryString
	 *            HQL查询语句
	 * 
	 * @return 查询语句返回的结果集
	 */
	@Override
	public List<?> findForList(String queryString) {
		return this.hibernateTemplate.find(queryString);
	}

	/**
	 * 执行HQL查询语句
	 * 
	 * @param queryString
	 *            HQL查询语句
	 * 
	 * @param value
	 *            单个查询参数(对应占位符"?")
	 * 
	 * @return 查询语句返回的结果集
	 */
	@Override
	public List<?> findForList(String queryString, Object value) {
		return this.hibernateTemplate.find(queryString, value);
	}

	/**
	 * 执行HQL查询语句
	 * 
	 * @param queryString
	 *            HQL查询语句
	 * 
	 * @param values
	 *            单个查询参数数组(对应占位符"?")
	 * 
	 * @return 查询语句返回的结果集
	 */
	@Override
	public List<?> findForList(String queryString, Object... values) {
		return this.hibernateTemplate.find(queryString, values);
	}

	/**
	 * 执行一个HQL语句,返回单个对象<br />
	 * 该方法建议只用作返回单挑记录的查询(如count或者update,insert,delete语句)<br />
	 * 如果查询语句返回的是多条记录则返回一个List
	 * 
	 * @param queryString
	 *            HQL语句
	 * @return HQL语句执行后返回的结果
	 */
	@Override
	public Object executeForObject(final String queryString) {
		return this
				.executeForObject(queryString, ArrayUtils.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * 执行一个HQL语句,返回单个对象<br />
	 * 该方法建议只用作返回单挑记录的查询(如count或者update,insert,delete语句)<br />
	 * 如果查询语句返回的是多条记录则返回一个List
	 * 
	 * @param queryString
	 *            HQL语句
	 * @param value
	 *            查询参数(对应占位符"?")
	 * @return HQL语句执行后返回的结果
	 */
	@Override
	public Object executeForObject(final String queryString, final Object value) {
		return this.executeForObject(queryString, new Object[] { value });
	}

	/**
	 * 执行一个HQL语句,返回单个对象<br />
	 * 该方法建议只用作返回单挑记录的查询(如count或者update,insert,delete语句)<br />
	 * 如果查询语句返回的是多条记录则返回一个List
	 * 
	 * @param queryString
	 *            HQL语句
	 * @param values
	 *            查询参数(对应占位符"?")
	 * @return HQL语句执行后返回的结果
	 */
	@Override
	public Object executeForObject(final String queryString,
			final Object... values) {

		return this.hibernateTemplate.execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Object result = null;
				Query query = session.createQuery(queryString);
				if (ArrayUtils.isNotEmpty(values)) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				try {
					result = query.uniqueResult();
				} catch (Exception e) {
					result = query.list();
				}
				return result;
			}

		});

	}

	/**
	 * 执行一个HQL查询语句返回单个结果
	 * 
	 * @param queryString
	 *            HQL查询语句
	 * 
	 * @exception DaoException
	 *                当查询语句返回多个记录时抛出
	 * 
	 * @return 查询语句返回的结果
	 */
	@Override
	public Object findSingle(String queryString) throws DaoException {
		return this.findSingle(queryString, ArrayUtils.EMPTY_OBJECT_ARRAY);

	}

	/**
	 * 执行一个HQL查询语句返回单个结果
	 * 
	 * @param queryString
	 *            HQL查询语句
	 * 
	 * @param value
	 *            查询参数(对应占位符"?")
	 * @exception DaoException
	 *                当查询语句返回多个记录时抛出
	 * 
	 * @return 查询语句返回的结果
	 */
	@Override
	public Object findSingle(String queryString, Object value)
			throws DaoException {
		return this.findSingle(queryString, new Object[] { value });
	}

	/**
	 * 执行一个HQL查询语句返回单个结果
	 * 
	 * @param queryString
	 *            HQL查询语句
	 * 
	 * @param values
	 *            查询参数数组(对应占位符"?")
	 * @exception DaoException
	 *                当查询语句返回多个记录时抛出
	 * 
	 * @return 查询语句返回的结果
	 */
	@Override
	public Object findSingle(String queryString, Object... values)
			throws DaoException {
		List<?> result = this.findForList(queryString, values);
		if (result == null || result.size() == 0) {
			return null;
		}
		if (result.size() > 1) {
			throw new DaoException("查询语句:[" + queryString
					+ "] 数据库唯一记录出错:期望是一条记录,但是查询到多条");
		}
		return result.get(0);
	}

	/**
	 * 执行一个HQL分页查询语句返回结果集
	 * 
	 * @param queryString
	 *            HQL查询语句
	 * @param firstResult
	 *            查询开始的记录行数
	 * @param maxResults
	 *            查询的记录的数量
	 * @return 查询语句返回的结果集
	 */
	@Override
	public List<?> findForList(final String queryString, final int firstResult,
			final int maxResults) {
		return this.findForList(queryString, firstResult, maxResults,
				new Object[] {});
	}

	/**
	 * 执行一个HQL分页查询语句返回结果集
	 * 
	 * @param queryString
	 *            HQL查询语句
	 * @param firstResult
	 *            查询开始的记录行数
	 * @param maxResults
	 *            查询的记录的数量
	 * @param value
	 *            查询参数(对应占位符"?")
	 * @return 查询语句返回的结果
	 */
	@Override
	public List<?> findForList(final String queryString, final int firstResult,
			final int maxResults, final Object value) {
		return this.findForList(queryString, firstResult, maxResults,
				new Object[] { value });
	}

	/**
	 * 执行一个HQL分页查询语句返回结果集
	 * 
	 * @param queryString
	 *            HQL查询语句
	 * @param firstResult
	 *            查询开始的记录行数
	 * @param maxResults
	 *            查询的记录的数量
	 * @param values
	 *            查询参数数组(对应占位符"?")
	 * @return 查询语句返回的结果集
	 */
	@Override
	public List<?> findForList(final String queryString, final int firstResult,
			final int maxResults, final Object... values) {

		List<?> resList = hibernateTemplate
				.execute(new HibernateCallback<List<?>>() {

					@Override
					public List<?> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(queryString);
						int i = 0;
						for (Object object : values) {
							query.setParameter(i, object);
							i++;
						}
						query.setFirstResult(firstResult);
						query.setMaxResults(maxResults);
						return query.list();
					}

				});
		return resList;
	}

	/**
	 * 基于一个对象实例执行查询
	 * 
	 * @param exampleEntity
	 *            对象实例
	 * 
	 * @return 查询语句返回的结果集 如果没有记录返回长度为0的集合
	 */
	@Override
	public List<?> findByExample(Object exampleEntity) {
		return this.hibernateTemplate.findByExample(exampleEntity);
	}

	/**
	 * 基于一个对象实例执行查询
	 * 
	 * @param exampleEntity
	 *            对象实例
	 * @param firstResult
	 *            查询开始的记录行数
	 * @param maxResults
	 *            查询的记录的数量
	 * 
	 * @return 查询语句返回的结果集 如果没有记录返回长度为0的集合
	 */
	@Override
	public List<?> findByExample(Object exampleEntity, int firstResult,
			int maxResults) {
		return this.hibernateTemplate.findByExample(exampleEntity, firstResult,
				maxResults);
	}

	/**
	 * 基于一个对象实例执行查询返回单条记录
	 * 
	 * @param exampleEntity
	 *            对象实例
	 * @exception DaoException
	 *                当返回记录为多条时抛出
	 * 
	 * @return 查询语句返回的结果
	 */
	@Override
	public Object findByExampleSingle(Object exampleEntity) throws DaoException {
		List<?> list = this.hibernateTemplate.findByExample(exampleEntity);
		if (list.size() == 1) {
			return list.get(0);
		}
		if (list.size() == 0) {
			return null;
		}
		throw new DaoException("查询语句:[" + exampleEntity.toString()
				+ "] 数据库唯一记录出错:期望是一条记录,但是查询到多条");
	}

	/**
	 * {@link #findEqual(Constraints)} 查询单条记录, 如果查询到多条抛出异常
	 */
	public <T> T findEqualSingle(FindEqualBuilder criterions)
			throws DaoException {
		List<T> list = findEqual(criterions, 0, 0);
		if (list.size() == 1) {
			return list.get(0);
		}
		if (list.size() == 0) {
			return null;
		}
		throw new DaoException("查询:[" + criterions.toString()
				+ "] 数据库唯一记录出错:期望是一条记录,但是查询到多条");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T findEqualSingle(Class<T> entityClass, Object paramObj,
			Map<String, String> mapping, String[] ignores) throws DaoException {
		FindEqualBuilder findEqualBuilder = FindEqualBuilder.getInstance()
				.setEntityClass(entityClass).setParamObj(paramObj)
				.setMapping(mapping).setIgnores(ignores);
		return (T) findEqualSingle(findEqualBuilder);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	@Override
	public <T> T findEqualSingle(Class<T> entityClass, Object paramObj,
			Map<String, String> mapping) throws DaoException {
		return findEqualSingle(entityClass, paramObj, mapping, null);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	@Override
	public <T> T findEqualSingle(Class<T> entityClass, Object paramObj,
			String[] ignores) throws DaoException {
		return findEqualSingle(entityClass, paramObj, null, ignores);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	@Override
	public <T> T findEqualSingle(Class<T> entityClass, Object paramObj)
			throws DaoException {
		return findEqualSingle(entityClass, paramObj, null, null);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	@Override
	public Object findEqualSingle(Object entity, String[] ignores)
			throws DaoException {
		return findEqualSingle(null, entity, null, ignores);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	@Override
	public Object findEqualSingle(Object entity) throws DaoException {
		return findEqualSingle(null, entity);
	}

	public long findEqualCount(FindEqualBuilder criterions) {
		criterions.init();
		final DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(criterions.getEntityClass());
		detachedCriteria.setProjection(Projections.rowCount());
		for (Criterion criterion : criterions.get()) {
			detachedCriteria.add(criterion);
		}
		return hibernateTemplate.execute(new HibernateCallback<Long>() {

			@Override
			public Long doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = detachedCriteria
						.getExecutableCriteria(session);
				Long result = (Long) criteria.uniqueResult();
				criteria.setProjection(null);
				criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
				return result;
			}
		});
	}

	/**
	 * 根据自定义约束查询
	 * 
	 * @param criterions
	 *            自定义约束{@link org.roof.dataaccess.Constraints}
	 * @param firstResult
	 *            查询开始的记录行数
	 * @param maxResults
	 *            查询的记录的数量
	 * @return 查询语句返回的结果
	 */
	public <T> List<T> findEqual(FindEqualBuilder criterions, int firstResult,
			int maxResults) {
		criterions.init();
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(criterions.getEntityClass());
		if (criterions.get() != null) {
			for (Criterion criterion : criterions.get()) {
				detachedCriteria.add(criterion);
			}
		}
		if (criterions.getOrders() != null) {
			for (Order order : criterions.getOrders()) {
				detachedCriteria.addOrder(order);
			}
		}
		@SuppressWarnings("unchecked")
		List<T> result = (List<T>) this.hibernateTemplate.findByCriteria(
				detachedCriteria, firstResult, maxResults);
		return result;
	}

	/**
	 * 根据自定义约束查询<br/>
	 * 根据值对象中的非空属性作为条件进行查询<br/>
	 * <b>建议: 在实体类中 提供返回 mapping ignores 的方法</b>
	 * 
	 * @param entityClass
	 *            实体类 , 为空时表示值对象就是实体类<br />
	 *            {@link #findByExample(Object)}
	 * @param paramObj
	 *            值对象, 不能为空
	 * @param mapping
	 *            实体类属性(key)和值对象属性(value)映射, 不设置mapping时默认实体类属性和值对象属性相同
	 * @param ignores
	 *            忽略的查询属性(对应实体类的属性), 不设置时会将值对象中所有非空属性作为查询条件
	 * @param firstResult
	 *            查询开始的记录行数
	 * @param maxResults
	 *            查询的记录的数量
	 * @return 查询语句返回的结果
	 */
	@Override
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj,
			Map<String, String> mapping, String[] ignores, int firstResult,
			int maxResults) {
		Assert.notNull(paramObj);
		FindEqualBuilder findEqualBuilder = FindEqualBuilder.getInstance()
				.setEntityClass(entityClass).setParamObj(paramObj)
				.setMapping(mapping).setIgnores(ignores);
		return findEqual(findEqualBuilder, firstResult, maxResults);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 */
	@Override
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj,
			Map<String, String> mapping, int firstResult, int maxResults) {
		return findEqual(entityClass, paramObj, mapping, null, firstResult,
				maxResults);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 */
	@Override
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj,
			String[] ignores, int firstResult, int maxResults) {
		return findEqual(entityClass, paramObj, null, ignores, firstResult,
				maxResults);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 */
	@Override
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj,
			int firstResult, int maxResults) {
		return findEqual(entityClass, paramObj, null, null, firstResult,
				maxResults);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 */
	@Override
	public List<?> findEqual(Object entity, String[] ignores, int firstResult,
			int maxResults) {
		return findEqual(null, entity, null, ignores, firstResult, maxResults);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 */
	@Override
	public List<?> findEqual(Object entity, int firstResult, int maxResults) {
		return findEqual(null, entity, firstResult, maxResults);
	}

	/**
	 * {@link #findEqual(Constraints)} 不带分页
	 */
	public <T> List<T> findEqual(FindEqualBuilder criterions) {
		return findEqual(criterions, -1, -1);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	@Override
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj,
			Map<String, String> mapping, String[] ignores) {
		return findEqual(entityClass, paramObj, mapping, ignores, -1, -1);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	@Override
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj,
			Map<String, String> mapping) {
		return findEqual(entityClass, paramObj, mapping, null);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	@Override
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj,
			String[] ignores) {
		return findEqual(entityClass, paramObj, null, ignores, -1, -1);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	@Override
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj) {
		return findEqual(entityClass, paramObj, null, null, -1, -1);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	@Override
	public List<?> findEqual(Object entity, String[] ignores) {
		return findEqual(null, entity, null, ignores, -1, -1);
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	@Override
	public List<?> findEqual(Object entity) {
		return findEqual(null, entity);
	}

	/**
	 * 更新一个持久化对象,并且绑定到当前Hibernate {@link org.hibernate.Session}
	 * 
	 * @param entity
	 *            需要更新的持久化对象
	 * @throws org.springframework.dao.DataAccessException
	 *             Hibernate errors 产生时抛出
	 * @see org.hibernate.Session#update(Object)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Object entity) {
		this.hibernateTemplate.update(entity);
	}

	/**
	 * 更新对象忽略对象内的空值<br />
	 * <b>注意</b> 所有的属性必须使用对象类型 ,如果为原生类型将被忽略无法更新
	 * 
	 * @param entity
	 *            需要更新的持久化对象
	 * @param id
	 *            主键
	 * @throws DaoException
	 *             产生内部转换异常时抛出
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Object updateIgnoreNull(Object entity, Serializable id) {
		return this.updateIgnoreNull(entity, id, new String[] {});
	}

	/**
	 * 更新对象忽略对象内的空值<br />
	 * <b>注意</b> 所有的属性必须使用对象类型 ,如果为原生类型将被忽略无法更新
	 * 
	 * @param entity
	 *            需要更新的持久化对象
	 * @param id
	 *            主键
	 * @param props
	 *            不忽略空值的属性数组
	 * @throws DaoException
	 *             产生内部转换异常时抛出
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Object updateIgnoreNull(Object entity, Serializable id,
			String[] props) {
		Object old = null;
		try {
			old = this.load(entity.getClass(), id);
			@SuppressWarnings("unchecked")
			Set<Entry<String, Object>> newSet = PropertyUtils.describe(entity)
					.entrySet();
			for (Entry<String, Object> newEntry : newSet) {
				boolean isIgnore = true;
				if (StringUtils.equals(newEntry.getKey(), "class")) {
					continue;
				}
				for (String prop : props) {
					if (prop.equals(newEntry.getKey())) {
						isIgnore = false;
						break;
					}
				}
				if ((isIgnore) && (newEntry.getValue() == null)) {
					continue;
				}
				PropertyUtils.setProperty(old, newEntry.getKey(),
						newEntry.getValue());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		this.saveOrUpdate(old);
		return old;
	}

	@Override
	public void saveOrUpdateIgnoreNull(Object entity) {
		Serializable id = getPrimaryKey(entity);
		if (id == null) {
			this.save(entity);
		} else {
			this.updateIgnoreNull(entity, id);
		}
	}

	/**
	 * 更新对象忽略对象内的空值<br />
	 * <b>注意</b> 所有的属性必须使用对象类型 ,如果为原生类型将被忽略无法更新
	 * 
	 * @param entity
	 *            需要更新的持久化对象
	 */
	@Override
	public Object updateIgnoreNull(Object entity) {
		Serializable id = getPrimaryKey(entity);
		return updateIgnoreNull(entity, id);
	}

	/**
	 * 判断一个类是否是实体类
	 * 
	 * @param cls
	 *            需要判断的类
	 * @return 是否为实体类
	 */
	@Override
	public boolean isEntity(Class<?> cls) {
		Entity e = cls.getAnnotation(Entity.class);
		if (e != null) {
			return true;
		}
		return false;
	}

	/**
	 * 将实体类id为null的属性替换成null防止报对象未持久化的异常
	 * 
	 * @param entity
	 *            需要替换的实体类
	 */
	@Override
	public void replaceEmptyToNull(Object entity) {
		try {
			Class<?> entityClass = entity.getClass();
			PropertyDescriptor[] descriptors = PropertyUtils
					.getPropertyDescriptors(entityClass);
			for (PropertyDescriptor propertyDescriptor : descriptors) {
				Object val = propertyDescriptor.getReadMethod().invoke(entity,
						ArrayUtils.EMPTY_OBJECT_ARRAY);
				if (val != null && isEntity(val.getClass())) {
					Serializable pk = getPrimaryKey(val);
					if (pk == null) {
						propertyDescriptor.getWriteMethod().invoke(entity,
								new Object[] { null });
					} else {
						replaceEmptyToNull(val);
					}
				}
			}
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 读取实体类的Id值
	 * 
	 * @param entity
	 *            需要读取的实体类
	 * @return 主键值
	 */
	@Override
	public Serializable getPrimaryKey(Object entity) {
		Serializable id = null;
		try {
			PropertyDescriptor idPropertyDescriptor = getPrimaryKeyProperty(entity
					.getClass());
			Method method = PropertyUtils.getReadMethod(idPropertyDescriptor);
			if (method != null && method.getAnnotation(Id.class) != null) {
				id = (Serializable) method.invoke(entity,
						ArrayUtils.EMPTY_OBJECT_ARRAY);
			}
		} catch (IllegalArgumentException e) {
			logger.error(e);
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (InvocationTargetException e) {
			logger.error(e);
		}
		return id;
	}

	/**
	 * 获得主键property属性
	 * 
	 * @param <T>
	 * 
	 * @param entity
	 *            需要读取的实体类
	 * @return property属性
	 */
	@Override
	public <T> PropertyDescriptor getPrimaryKeyProperty(Class<T> entityClass) {
		try {
			PropertyDescriptor[] descriptors = PropertyUtils
					.getPropertyDescriptors(entityClass);
			for (PropertyDescriptor propertyDescriptor : descriptors) {
				Method method = PropertyUtils.getReadMethod(propertyDescriptor);
				if (method != null && method.getAnnotation(Id.class) != null) {
					return propertyDescriptor;
				}
				if (StringUtils.equals(propertyDescriptor.getDisplayName(),
						"class")) {
					continue;
				}
				Field field = null;
				try {
					field = entityClass.getDeclaredField(propertyDescriptor
							.getDisplayName());
				} catch (NoSuchFieldException e) {
					logger.debug(e);
				}
				if (field != null && field.getAnnotation(Id.class) != null) {
					return propertyDescriptor;
				}
			}
		} catch (SecurityException e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 更具持久化对象的主键保存或者更新对象.绑定对象到当前的Hibernate {@link org.hibernate.Session}.
	 * 
	 * 
	 * @param entity
	 *            需要保存或者更新的持久化对象(将会绑定到当前的Hibernate <code>Session</code>)
	 * 
	 * @throws DataAccessException
	 *             Hibernate errors 产生时抛出
	 * @see org.hibernate.Session#saveOrUpdate(Object)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrUpdate(Object entity) {
		this.hibernateTemplate.saveOrUpdate(entity);
	}

	/**
	 * 更具持久化对象的主键保存或者更新对象集合.绑定对象到当前的Hibernate {@link org.hibernate.Session}.
	 * 
	 * 
	 * @param entities
	 *            需要保存或者更新的持久化对象集合(将会绑定到当前的Hibernate <code>Session</code>)
	 * 
	 * @throws DataAccessException
	 *             Hibernate errors 产生时抛出
	 * @see org.hibernate.Session#saveOrUpdate(Object)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrUpdateAll(Collection<?> entities) {
		for (Object entity : entities) {
			this.hibernateTemplate.saveOrUpdate(entity);
		}
	}

	/**
	 * 更具持久化对象的主键保存或者更新对象集合.绑定对象到当前的Hibernate {@link org.hibernate.Session}.
	 * 保存时会忽略null值
	 * 
	 * @see {@link #updateIgnoreNull(Object)}
	 * @param entities
	 *            需要保存或者更新的持久化对象集合(将会绑定到当前的Hibernate <code>Session</code>)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateAllIgnoreNull(Collection<?> entities) {
		for (Object entity : entities) {
			this.updateIgnoreNull(entity);
		}
	}

	/**
	 * 执行一个HQL查询, 将Bean的属性绑定到查询语句命名的参数上
	 * 
	 * @param queryString
	 *            HQL查询语句
	 * @param valueBean
	 *            参数的值对象
	 * @return 包含返回结果的集合 {@link List}
	 * @throws org.springframework.dao.DataAccessException
	 *             Hibernate 异常产生时抛出
	 * @see org.hibernate.Query#setProperties
	 * @see org.hibernate.Session#createQuery
	 */
	@Override
	public List<?> selectByValueBean(String queryString, Object valueBean) {
		return this.hibernateTemplate.findByValueBean(queryString, valueBean);
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 执行一个映射的SQL SELECT语句返回查询获得的结果集<br />
	 * SELECT 语句不接收参数
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @return 包含返回结果的集合 {@link List}
	 * @throws java.sql.SQLException
	 *             SQL异常产生时抛出
	 */
	@Override
	public List<?> selectForList(String statementName) {
		return this.sqlSessionTemplate.selectList(statementName);
	}

	/**
	 * 执行一个映射的SQL SELECT语句返回查询获得的结果集
	 * <p/>
	 * 参数对象通常用于为SELECT语句的WHERE查询条件提供输入数据
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @param parameterObject
	 *            参数对象 (e.g. JavaBean, Map, XML etc.).
	 * @return 包含返回结果的集合 {@link List}
	 * @throws java.sql.SQLException
	 *             SQL异常产生时抛出
	 */
	@Override
	public List<?> selectForList(String statementName, Object parameterObject) {
		return this.sqlSessionTemplate.selectList(statementName,
				parameterObject);
	}

	/**
	 * 执行一个映射的SQL SELECT语句返回查询获得的结果集
	 * <p/>
	 * SELECT 语句不接收参数
	 * <p/>
	 * <b>不建议使用</b>
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @param skipResults
	 *            跳过的记录行数
	 * @param maxResults
	 *            一次读取的最大行数
	 * @return 包含返回结果的集合 {@link List}
	 * @throws java.sql.SQLException
	 *             SQL异常产生时抛出
	 */
	@Override
	@Deprecated
	public List<?> selectForList(String statementName, int skipResults,
			int maxResults) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 执行一个映射的SQL SELECT语句返回查询获得的结果集
	 * <p/>
	 * 参数对象通常用于为SELECT语句的WHERE查询条件提供输入数据
	 * <p/>
	 * <b>不建议使用</b>
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @param parameterObject
	 *            参数对象 (e.g. JavaBean, Map, XML etc.).
	 * @param skipResults
	 *            跳过的记录行数
	 * @param maxResults
	 *            一次读取的最大行数
	 * @return 包含返回结果的集合 {@link List}
	 * @throws java.sql.SQLException
	 *             SQL异常产生时抛出
	 */
	@Override
	@Deprecated
	public List<?> selectForList(String statementName, Object parameterObject,
			int skipResults, int maxResults) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 执行一个映射的SQL SELECT语句将返回的数据填充到一个对象中
	 * <p/>
	 * SELECT 语句不接收参数
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @return 填充了查询结果的一个对象,当没有返回结果的时候为null
	 * 
	 * @throws java.sql.SQLException
	 *             如果查询到多条记录 ,或者其他的SQL异常
	 */
	@Override
	public Object selectForObject(String statementName) {
		return this.sqlSessionTemplate.selectOne(statementName);
	}

	/**
	 * 执行一个映射的SQL SELECT语句将返回的数据填充到一个对象中
	 * <p/>
	 * 参数对象通常用于为SELECT语句的WHERE查询条件提供输入数据
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @param parameterObject
	 *            参数对象 (e.g. JavaBean, Map, XML etc.).
	 * @return 填充了查询结果的一个对象,当没有返回结果的时候为null
	 * @throws java.sql.SQLException
	 *             如果查询到多条记录 ,或者其他的SQL异常
	 */
	@Override
	public Object selectForObject(String statementName, Object parameterObject) {
		return this.sqlSessionTemplate
				.selectOne(statementName, parameterObject);
	}

	/**
	 * 执行一个映射的SQL UPDATE语句 ,Update同样可以用于其他的更新类型如insert和delete. 返回影响记录的行数 *
	 * <p/>
	 * UPDATE 语句不接收参数
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @return 返回影响记录的行数
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int update(String statementName) {
		return sqlSessionTemplate.update(statementName);
	}

	/**
	 * 执行一个映射的SQL UPDATE语句 ,Update同样可以用于其他的更新类型如insert和delete. 返回影响记录的行数
	 * <p/>
	 * 参数对象通常用于为UPDATE语句的WHERE查询条件提供输入数据
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @param parameterObject
	 *            参数对象 (e.g. JavaBean, Map, XML etc.).
	 * @return 返回影响记录的行数
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int update(String statementName, Object parameterObject) {
		return sqlSessionTemplate.update(statementName, parameterObject);
	}

	/**
	 * 执行一个映射的SQL UPDATE语句 ,Update同样可以用于其他的更新类型如insert和delete.<br />
	 * 当实际影响的行数和需要影响的行数不相等时抛出异常
	 * <p/>
	 * 参数对象通常用于为UPDATE语句的WHERE查询条件提供输入数据
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @param parameterObject
	 *            参数对象 (e.g. JavaBean, Map, XML etc.).
	 * @param requiredRowsAffected
	 *            需要影响的行数
	 * @throws JdbcUpdateAffectedIncorrectNumberOfRowsException
	 *             如果实际影响的行数和需要影响的行数不相等抛出
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(String statementName, Object parameterObject,
			int requiredRowsAffected) {
		int i = sqlSessionTemplate.update(statementName, parameterObject);
		if (requiredRowsAffected != i) {
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
					statementName, requiredRowsAffected, i);
		}
	}

	/**
	 * 执行一个映射的SQL INSERT语句, 返回产生的主键
	 * <p/>
	 * INSERT 语句不接收参数
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @return 产生的主键
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Object save(String statementName) {
		return sqlSessionTemplate.insert(statementName);
	}

	/**
	 * 执行一个映射的SQL INSERT语句, 返回产生的主键
	 * <p/>
	 * 参数对象通常用于为INSERT语句的WHERE查询条件提供输入数据
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @param parameterObject
	 *            参数对象 (e.g. JavaBean, Map, XML etc.).
	 * @return 产生的主键
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Object save(String statementName, Object parameterObject) {
		return sqlSessionTemplate.insert(statementName, parameterObject);
	}

	/**
	 * 执行一个映射的SQL DELETE语句 .返回影响记录的行数
	 * <p/>
	 * DELETE 语句不接收参数
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * 
	 * @return 影响记录的行数
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int delete(String statementName) {
		return sqlSessionTemplate.delete(statementName);
	}

	/**
	 * 执行一个映射的SQL DELETE语句 .返回影响记录的行数
	 * <p/>
	 * 参数对象通常用于为INSERT语句的WHERE查询条件提供输入数据
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @param parameterObject
	 *            参数对象 (e.g. JavaBean, Map, XML etc.).
	 * @return 影响记录的行数
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int delete(String statementName, Object parameterObject) {
		return sqlSessionTemplate.delete(statementName, parameterObject);
	}

	/**
	 * 执行一个映射的SQL DELETE语句 <br />
	 * 当实际影响的行数和需要影响的行数不相等时抛出异常
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @param parameterObject
	 *            参数对象 (e.g. JavaBean, Map, XML etc.).
	 * @param requiredRowsAffected
	 *            如果实际影响的行数和需要影响的行数不相等抛出
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(String statementName, Object parameterObject,
			int requiredRowsAffected) {
		int i = sqlSessionTemplate.delete(statementName, parameterObject);
		if (requiredRowsAffected != i) {
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
					statementName, requiredRowsAffected, i);
		}
	}

	/**
	 * 执行一个映射的SQL SELECT语句, 将返回的结果填充到Map中,返回一个Map的结果集
	 * <p/>
	 * 由于java的命名习惯与数据库不同,该方法将Map的key下划线分割的字符串的形式转化为驼峰形式<br />
	 * 如 statement_name 对应 statementName<br />
	 * _statement_name 对应 Statement_name
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @return 包含返回结果的集合 {@link List}
	 * @throws DaoException
	 *             JavaBean-Map映射出现异常是抛出
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectForCamelCaseMap(String statementName)
			throws DaoException {
		List<?> resultList = sqlSessionTemplate.selectList(statementName);
		if (CollectionUtils.isEmpty(resultList)) {
			return Collections.emptyList();
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Object element = resultList.get(0);
		if (ClassUtils.isAssignable(element.getClass(), Map.class)) {
			RoofMapUtils.keyToCamelCase((List<Map<String, Object>>) resultList);
			return (List<Map<String, Object>>) resultList;
		} else {
			for (Object e : resultList) {
				try {
					result.add(PropertyUtils.describe(e));
				} catch (Exception exp) {
					throw new DaoException("JavaBean-Map映射出错!", exp);
				}
			}
		}
		return result;
	}

	// ////////////////////////////////////////////////////////////////////////////////
	@Override
	@Deprecated
	public List<Map<String, Object>> selectForMap(String sql) {
		return selectForMap(sql, ArrayUtils.EMPTY_OBJECT_ARRAY);
	}

	@Override
	@Deprecated
	public List<Map<String, Object>> selectForMap(String sql, Object value) {
		return selectForMap(sql, new Object[] { value });
	}

	@Override
	@Deprecated
	public List<Map<String, Object>> selectForMap(String sql, Object... values) {
		List<Map<String, Object>> result = jdbcTemplate.query(sql,
				new RowMapper<Map<String, Object>>() {

					@Override
					public Map<String, Object> mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Map<String, Object> map = new HashMap<String, Object>();
						int n = rs.getMetaData().getColumnCount();
						for (int i = 1; i <= n; i++) {
							map.put(rs.getMetaData().getColumnLabel(i),
									rs.getObject(i));
						}
						return map;
					}

				}, values);
		return result;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 分页执行一个映射的HQL SELECT语句
	 * <p/>
	 * SELECT 语句不接收参数
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @param firstResult
	 *            开始查询的记录行数
	 * @param maxResults
	 *            读取的最大记录行数
	 * @return 查询语句返回的结果
	 */
	@Override
	public List<?> findByMappedQuery(String statementName, int firstResult,
			int maxResults) {
		SqlDescription description = this.getNamedSql(statementName, null);
		return this.findForList(description.getSql(), firstResult, maxResults);
	}

	/**
	 * 分页执行一个映射的HQL SELECT语句
	 * <p/>
	 * 参数对象通常用于为SELECT语句的WHERE查询条件提供输入数据
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @param firstResult
	 *            开始查询的记录行数
	 * @param maxResults
	 *            读取的最大记录行数
	 * @param parameterObject
	 *            参数对象 (e.g. JavaBean, Map, XML etc.).
	 * @return 查询语句返回的结果
	 */
	@Override
	public List<?> findByMappedQuery(String statementName, int firstResult,
			int maxResults, Object parameterObject) {
		SqlDescription description = this.getNamedSql(statementName,
				parameterObject);
		return this.findForList(description.getSql(), firstResult, maxResults,
				description.getParameters());
	}

	/**
	 * 执行一个映射的HQL SELECT语句, 返回一个结果集合
	 * <p/>
	 * SELECT 语句不接收参数
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @return 查询语句返回的结果
	 */
	@Override
	public List<?> findByMappedQuery(String statementName) {
		SqlDescription description = this.getNamedSql(statementName, null);
		return this.findForList(description.getSql());
	}

	/**
	 * 执行一个映射的HQL SELECT语句, 返回一个结果集合
	 * <p/>
	 * 参数对象通常用于为SELECT语句的WHERE查询条件提供输入数据
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @param parameterObject
	 *            参数对象 (e.g. JavaBean, Map, XML etc.).
	 * @return 查询语句返回的结果
	 */
	@Override
	public List<?> findByMappedQuery(String statementName,
			Object parameterObject) {
		SqlDescription description = this.getNamedSql(statementName,
				parameterObject);
		return this.findForList(description.getSql(),
				description.getParameters());
	}

	/**
	 * 执行一个映射的HQL SELECT语句, 返回单个对象
	 * <p />
	 * SELECT 语句不接收参数
	 * <p />
	 * <b>当查询返回多条记录时抛出异常</b>
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @return 查询语句返回的结果
	 * @throws DaoException
	 *             当查询返回多条记录时抛出
	 */
	@Override
	public Object findByMappedQuerySingle(String statementName)
			throws DaoException {
		SqlDescription description = this.getNamedSql(statementName, null);
		return this.findSingle(description.getSql());
	}

	/**
	 * 执行一个映射的HQL SELECT语句, 返回单个对象
	 * <p />
	 * 参数对象通常用于为SELECT语句的WHERE查询条件提供输入数据
	 * <p />
	 * <b>当查询返回多条记录时抛出异常</b>
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @param parameterObject
	 *            参数对象 (e.g. JavaBean, Map, XML etc.).
	 * @return 查询语句返回的结果
	 * @throws DaoException
	 *             当查询返回多条记录时抛出
	 */
	@Override
	public Object findByMappedQuerySingle(String statementName,
			Object parameterObject) throws DaoException {
		SqlDescription description = this.getNamedSql(statementName,
				parameterObject);
		return this.findSingle(description.getSql(),
				description.getParameters());
	}

	/**
	 * 执行一个映射的HQL语句, 返回单个对象<br/>
	 * 可以执行任意的HQL语句
	 * <p />
	 * HQL 语句不接收参数
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @return 语句返回的结果
	 */
	@Override
	public Object executeByMappedQuery(String statementName) {
		SqlDescription description = this.getNamedSql(statementName, null);
		return this.executeForObject(description.getSql());
	}

	/**
	 * 执行一个映射的HQL语句, 返回单个对象<br/>
	 * 可以执行任意的HQL语句 参数对象通常用于为SELECT语句的WHERE查询条件提供输入数据
	 * <p />
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * 
	 * @param parameterObject
	 *            参数对象 (e.g. JavaBean, Map, XML etc.).
	 * @return 语句返回的结果
	 */
	@Override
	public Object executeByMappedQuery(String statementName,
			Object parameterObject) {
		SqlDescription description = this.getNamedSql(statementName,
				parameterObject);
		return this.executeForObject(description.getSql(),
				description.getParameters());
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Resource(name = "jdbcTemplate")
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
