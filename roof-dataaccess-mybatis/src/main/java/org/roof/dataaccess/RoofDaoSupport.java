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
import org.hibernate.LockMode;
import org.roof.commons.RoofMapUtils;
import org.roof.roof.dataaccess.api.DaoException;
import org.roof.roof.dataaccess.api.IdGenerator;
import org.roof.roof.dataaccess.api.SqlDescription;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据访问对象基类,提供了数据库访问的基础方法<br />
 * 如果这些方法都无法满足业务需求,可以考虑使用该类提供的 sqlSessionTemplate, jdbcTemplate.
 * 
 * @author liuxin 2011-4-16
 * @version 2.0 RoofDaoSupport.java liuxin 2011-9-15
 */
@Transactional
public class RoofDaoSupport extends RoofAbstractDao {

	private static final Logger logger = Logger.getLogger(RoofDaoSupport.class);
	private static final String[] FIND_SUB_PACKAGES = new String[] { "dao", "dao.impl", "" };
	private String[] statementScanSubPackages = FIND_SUB_PACKAGES; // myBatis句柄扫描的子包
	private IdGenerator idGenerator; // ID生成器

	/**
	 * 重置lazy对象session
	 * 
	 * @param value
	 *            lazy的对象
	 */
	@Override
	public void resetSession(Object value) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
	public SqlDescription getNamedSql(String statementName, Object parameterObject) {
		org.apache.ibatis.mapping.MappedStatement mappedStatement = sqlSessionTemplate.getSqlSessionFactory()
				.getConfiguration().getMappedStatement(statementName);
		TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
		Configuration configuration = mappedStatement.getConfiguration();
		BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
		String hql = boundSql.getSql();
		if (parameterObject == null) {
			return new SqlDescription(hql, null);
		}

		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
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
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else {
						MetaObject metaObject = configuration.newMetaObject(parameterObject);
						value = metaObject.getValue(propertyName);
					}
					parameters[i] = value;
				}
			}
		}
		return new SqlDescription(hql, parameters);
	}

	private String getStatementName(String opName, Class<?> entityClass) {
		String packageName = entityClass.getPackage().getName();
		if (StringUtils.endsWith(packageName, ".entity")) {
			packageName = StringUtils.substringBeforeLast(packageName, ".");
		}
		Collection<String> statementNames = sqlSessionTemplate.getConfiguration().getMappedStatementNames();
		String rs = null;
		for (String pkg : statementScanSubPackages) {
			rs = packageName.concat(".").concat(pkg).concat(".").concat(opName);
			if (statementNames.contains(rs)) {
				return rs;
			}
		}
		return opName;
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
		if (entity == null) {
			return null;
		}
		Class<?> entityClass = entity.getClass();
		PropertyDescriptor idPropertyDescriptor = getPrimaryKeyProperty(entityClass);
		Serializable id = null;
		if (idGenerator != null && getPrimaryKey(entity) == null) {
			id = idGenerator.setId(entity, idPropertyDescriptor);
		}
		sqlSessionTemplate.insert(getStatementName("save" + entityClass.getSimpleName(), entityClass), entity);
		if (id != null) {
			try {
				idPropertyDescriptor.getWriteMethod().invoke(entity, id);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				logger.error(e.getMessage(), e);
			}
			return id;
		}
		return getPrimaryKey(entity, idPropertyDescriptor);
	}

	/**
	 * 将一个持久化对象转化为瞬时对象
	 * 
	 * @param entity
	 *            需要持久化的瞬时对象
	 */
	@Override
	public void evict(Object entity) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * 删除一个持久化对象，按主键
	 * 
	 * @param entity
	 *            需要删除的持久化对象
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Object entity) {
		Class<?> entityClass = entity.getClass();
		sqlSessionTemplate.delete(getStatementName("delete" + entityClass.getSimpleName(), entityClass), entity);
	}

	/**
	 * 基于一个对象实例删除持久化对象，按非空属性
	 * 
	 * @param entity
	 *            对象实例
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteByExample(Object entity) {
		Class<?> entityClass = entity.getClass();
		sqlSessionTemplate.delete(getStatementName("deleteByExample" + entityClass.getSimpleName(), entityClass),
				entity);
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
	@SuppressWarnings("unchecked")
	@Override
	public <T> T load(Class<T> entityClass, Serializable id) {
		Object o = null;
		Method method = PropertyUtils.getWriteMethod(getPrimaryKeyProperty(entityClass));
		if (method != null) {
			try {
				o = entityClass.newInstance();
				method.invoke(o, id);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			throw new IllegalArgumentException(entityClass.getName() + "主键必须标注@javax.persistence.Id或者以id命名");
		}
		return (T) reload(o);
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
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
		Class<?> entityClass = entity.getClass();
		return sqlSessionTemplate.selectOne(getStatementName("load" + entityClass.getSimpleName(), entityClass),
				entity);
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
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
		try {
			return sqlSessionTemplate.selectList(getStatementName("select" + entityClass.getSimpleName(), entityClass),
					entityClass.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
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
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
	public Object executeForObject(final String queryString, final Object... values) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
	public Object findSingle(String queryString, Object value) throws DaoException {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
	public Object findSingle(String queryString, Object... values) throws DaoException {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
	public List<?> findForList(final String queryString, final int firstResult, final int maxResults) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
	public List<?> findForList(final String queryString, final int firstResult, final int maxResults,
			final Object value) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
	public List<?> findForList(final String queryString, final int firstResult, final int maxResults,
			final Object... values) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
	public List<?> findByExample(Object exampleEntity, int firstResult, int maxResults) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link #findEqual(Constraints)} 查询单条记录, 如果查询到多条抛出异常
	 */
	public <T> T findEqualSingle(FindEqualBuilder criterions) throws DaoException {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	@Override
	public <T> T findEqualSingle(Class<T> entityClass, Object paramObj, Map<String, String> mapping, String[] ignores)
			throws DaoException {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	@Override
	public <T> T findEqualSingle(Class<T> entityClass, Object paramObj, Map<String, String> mapping) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	@Override
	public <T> T findEqualSingle(Class<T> entityClass, Object paramObj, String[] ignores) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	@Override
	public <T> T findEqualSingle(Class<T> entityClass, Object paramObj) throws DaoException {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	@Override
	public Object findEqualSingle(Object entity, String[] ignores) throws DaoException {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	@Override
	public Object findEqualSingle(Object entity) throws DaoException {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	public long findEqualCount(FindEqualBuilder criterions) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
	public <T> List<T> findEqual(FindEqualBuilder criterions, int firstResult, int maxResults) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj, Map<String, String> mapping, String[] ignores,
			int firstResult, int maxResults) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 */
	@Override
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj, Map<String, String> mapping, int firstResult,
			int maxResults) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 */
	@Override
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj, String[] ignores, int firstResult,
			int maxResults) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 */
	@Override
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj, int firstResult, int maxResults) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 */
	@Override
	public List<?> findEqual(Object entity, String[] ignores, int firstResult, int maxResults) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 */
	@Override
	public List<?> findEqual(Object entity, int firstResult, int maxResults) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link #findEqual(Constraints)} 不带分页
	 */
	public <T> List<T> findEqual(FindEqualBuilder criterions) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	@Override
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj, Map<String, String> mapping, String[] ignores) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	@Override
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj, Map<String, String> mapping) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	@Override
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj, String[] ignores) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	@Override
	public <T> List<T> findEqual(Class<T> entityClass, Object paramObj) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	@Override
	public List<?> findEqual(Object entity, String[] ignores) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	@Override
	public List<?> findEqual(Object entity) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
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
		Class<?> entityClass = entity.getClass();
		super.sqlSessionTemplate.update(getStatementName("update" + entityClass.getSimpleName(), entityClass), entity);
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
		Class<?> entityClass = entity.getClass();
		return super.sqlSessionTemplate
				.update(getStatementName("updateIgnoreNull" + entityClass.getSimpleName(), entityClass), entity);
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
	public Object updateIgnoreNull(Object entity, Serializable id, String[] props) {
		throw new UnsupportedOperationException("该方法为hibernate实现专有方法!");
	}

	@Override
	public void saveOrUpdateIgnoreNull(Object entity) {
		if (getPrimaryKey(entity) == null) {
			save(entity);
		} else {
			saveOrUpdateIgnoreNull(entity);
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
		Class<?> entityClass = entity.getClass();
		super.sqlSessionTemplate.update(getStatementName("updateIgnoreNull" + entityClass.getSimpleName(), entityClass),
				entity);
		return entity;
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
		PropertyDescriptor pk = getPrimaryKeyProperty(cls);
		if (pk != null) {
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
			PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(entityClass);
			for (PropertyDescriptor propertyDescriptor : descriptors) {
				Object val = propertyDescriptor.getReadMethod().invoke(entity, ArrayUtils.EMPTY_OBJECT_ARRAY);
				if (val != null && isEntity(val.getClass())) {
					Serializable pk = getPrimaryKey(val);
					if (pk == null) {
						propertyDescriptor.getWriteMethod().invoke(entity, new Object[] { null });
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
		PropertyDescriptor idPropertyDescriptor = getPrimaryKeyProperty(entity.getClass());
		return getPrimaryKey(entity, idPropertyDescriptor);
	}

	private Serializable getPrimaryKey(Object entity, PropertyDescriptor idPropertyDescriptor) {
		Serializable id = null;
		try {
			Method method = PropertyUtils.getReadMethod(idPropertyDescriptor);
			if (method != null) {
				id = (Serializable) method.invoke(entity, ArrayUtils.EMPTY_OBJECT_ARRAY);
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
		PropertyDescriptor idNamePropertyDescriptor = null;
		try {
			PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(entityClass);
			for (PropertyDescriptor propertyDescriptor : descriptors) {
				if (StringUtils.equalsIgnoreCase(propertyDescriptor.getDisplayName(), "id")) {
					idNamePropertyDescriptor = propertyDescriptor;
				}
				Method method = PropertyUtils.getReadMethod(propertyDescriptor);
				if (method != null && method.getAnnotation(Id.class) != null) {
					return propertyDescriptor;
				}
				if (StringUtils.equals(propertyDescriptor.getDisplayName(), "class")) {
					continue;
				}
				Field field = null;
				try {
					field = entityClass.getDeclaredField(propertyDescriptor.getDisplayName());
				} catch (NoSuchFieldException e) {
					logger.debug(e);
				}
				if (field != null && field.getAnnotation(Id.class) != null) {
					return propertyDescriptor;
				}
			}
			return idNamePropertyDescriptor;
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
		if (getPrimaryKey(entity) == null) {
			save(entity);
		} else {
			update(entity);
		}
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
		for (Object e : entities) {
			saveOrUpdate(e);
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
		for (Object e : entities) {
			updateIgnoreNull(e);
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
		throw new UnsupportedOperationException("该方法为hibernate方法!");
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
		return selectForList(statementName, null);
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
		if (parameterObject == null) {
			return super.sqlSessionTemplate.selectList(statementName);
		}
		return super.sqlSessionTemplate.selectList(statementName, parameterObject);
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
	public List<?> selectForList(String statementName, int skipResults, int maxResults) {
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
	public List<?> selectForList(String statementName, Object parameterObject, int skipResults, int maxResults) {
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
		return selectForObject(statementName, null);
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
		return super.sqlSessionTemplate.selectOne(statementName, parameterObject);
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
		return update(statementName, null);
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
		if (parameterObject == null) {
			return sqlSessionTemplate.update(statementName);
		}
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
	public void update(String statementName, Object parameterObject, int requiredRowsAffected) {
		int i = update(statementName, parameterObject);
		if (requiredRowsAffected != i) {
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(statementName, requiredRowsAffected, i);
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
		return save(statementName, null);
	}

	/**
	 * 执行一个映射的SQL INSERT语句, 不会调用主键生成
	 * <p/>
	 * 参数对象通常用于为INSERT语句的WHERE查询条件提供输入数据
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @param parameterObject
	 *            参数对象 (e.g. JavaBean, Map, XML etc.).
	 * @return 更新的行数
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Object save(String statementName, Object parameterObject) {
		if (parameterObject == null) {
			return sqlSessionTemplate.insert(statementName);
		}
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
		return delete(statementName, null);
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
		if (parameterObject == null) {
			return sqlSessionTemplate.delete(statementName);
		}
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
	public void delete(String statementName, Object parameterObject, int requiredRowsAffected) {
		int i = delete(statementName, parameterObject);
		if (requiredRowsAffected != i) {
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(statementName, requiredRowsAffected, i);
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
	public List<Map<String, Object>> selectForCamelCaseMap(String statementName) throws DaoException {
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
		List<Map<String, Object>> result = jdbcTemplate.query(sql, new RowMapper<Map<String, Object>>() {

			@Override
			public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				int n = rs.getMetaData().getColumnCount();
				for (int i = 1; i <= n; i++) {
					map.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
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
	public List<?> findByMappedQuery(String statementName, int firstResult, int maxResults) {
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
	public List<?> findByMappedQuery(String statementName, int firstResult, int maxResults, Object parameterObject) {
		SqlDescription description = this.getNamedSql(statementName, parameterObject);
		return this.findForList(description.getSql(), firstResult, maxResults, description.getParameters());
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
	public List<?> findByMappedQuery(String statementName, Object parameterObject) {
		SqlDescription description = this.getNamedSql(statementName, parameterObject);
		return this.findForList(description.getSql(), description.getParameters());
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
	public Object findByMappedQuerySingle(String statementName) throws DaoException {
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
	public Object findByMappedQuerySingle(String statementName, Object parameterObject) throws DaoException {
		SqlDescription description = this.getNamedSql(statementName, parameterObject);
		return this.findSingle(description.getSql(), description.getParameters());
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
	public Object executeByMappedQuery(String statementName, Object parameterObject) {
		SqlDescription description = this.getNamedSql(statementName, parameterObject);
		return this.executeForObject(description.getSql(), description.getParameters());
	}

	public IdGenerator getIdGenerator() {
		return idGenerator;
	}

	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	public String[] getStatementScanSubPackages() {
		return statementScanSubPackages;
	}

	public void setStatementScanSubPackages(String[] statementScanSubPackages) {
		this.statementScanSubPackages = statementScanSubPackages;
	}

}
