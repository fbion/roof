package org.roof.roof.dataaccess.api;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IDaoSupport {

	/**
	 * 重置lazy对象session
	 * 
	 * @param value
	 *            lazy的对象
	 */
	public abstract void resetSession(Object value);

	/**
	 * 获取XML文件中组装完成的SQL语句
	 * 
	 * @param statementName
	 *            查询语句 id
	 * @param parameterObject
	 *            参数对象
	 * @return 拼装后的查询语句和参数列表
	 */
	public abstract SqlDescription getNamedSql(String statementName,
			Object parameterObject);

	/**
	 * 持久化一个瞬时对象
	 * 
	 * @param entity
	 *            需要持久化的瞬时对象
	 * @return 生成的id
	 */
	public abstract Serializable save(Object entity);

	/**
	 * 将一个持久化对象转化为瞬时对象
	 * 
	 * @param entity
	 *            需要持久化的瞬时对象
	 */
	public abstract void evict(Object entity);

	/**
	 * 按主键删除一个持久化对象
	 * 
	 * @param entity
	 *            需要删除的持久化对象
	 */
	public abstract void delete(Object entity);

	/**
	 * 基于一个对象非空属性实例删除持久化对象
	 * 
	 * @param exampleEntity
	 *            对象实例
	 * 
	 */
	public abstract void deleteByExample(Object exampleEntity);

	/**
	 * 根据ID延迟加载持久化对象
	 * 
	 * @param entityClass
	 *            类
	 * @param id
	 *            主键
	 * @return 加载的持久化对象
	 */
	public abstract <T> T load(Class<T> entityClass, Serializable id);

	/**
	 * 根据ID重新延迟加载持久化对象
	 * 
	 * @param entity
	 *            带有主键的实体类
	 * @return 加载的持久化对象
	 */
	public abstract Object reload(Object entity);

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
	public abstract <T> T load(Class<T> entityClass, Serializable id,
			boolean lazy);

	/**
	 * 加载所有的持久化对象
	 * 
	 * @param entityClass
	 *            类
	 * @return 加载的持久化对象
	 */
	public abstract <T> List<T> loadAll(Class<T> entityClass);

	/**
	 * 执行HQL查询语句
	 * 
	 * @param queryString
	 *            HQL查询语句
	 * 
	 * @return 查询语句返回的结果集
	 */
	public abstract List<?> findForList(String queryString);

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
	public abstract List<?> findForList(String queryString, Object value);

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
	public abstract List<?> findForList(String queryString, Object... values);

	/**
	 * 执行一个HQL语句,返回单个对象<br />
	 * 该方法建议只用作返回单挑记录的查询(如count或者update,insert,delete语句)<br />
	 * 如果查询语句返回的是多条记录则返回一个List
	 * 
	 * @param queryString
	 *            HQL语句
	 * @return HQL语句执行后返回的结果
	 */
	public abstract Object executeForObject(String queryString);

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
	public abstract Object executeForObject(String queryString, Object value);

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
	public abstract Object executeForObject(String queryString,
			Object... values);

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
	public abstract Object findSingle(String queryString) throws DaoException;

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
	public abstract Object findSingle(String queryString, Object value)
			throws DaoException;

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
	public abstract Object findSingle(String queryString, Object... values)
			throws DaoException;

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
	public abstract List<?> findForList(String queryString, int firstResult,
			int maxResults);

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
	public abstract List<?> findForList(String queryString, int firstResult,
			int maxResults, Object value);

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
	public abstract List<?> findForList(String queryString, int firstResult,
			int maxResults, Object... values);

	/**
	 * 基于一个对象实例执行查询
	 * 
	 * @param exampleEntity
	 *            对象实例
	 * 
	 * @return 查询语句返回的结果集 如果没有记录返回长度为0的集合
	 */
	public abstract List<?> findByExample(Object exampleEntity);

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
	public abstract List<?> findByExample(Object exampleEntity,
			int firstResult, int maxResults);

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
	public abstract Object findByExampleSingle(Object exampleEntity)
			throws DaoException;

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	public abstract <T> T findEqualSingle(Class<T> entityClass,
			Object paramObj, Map<String, String> mapping, String[] ignores)
			throws DaoException;

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	public abstract <T> T findEqualSingle(Class<T> entityClass,
			Object paramObj, Map<String, String> mapping) throws DaoException;

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	public abstract <T> T findEqualSingle(Class<T> entityClass,
			Object paramObj, String[] ignores) throws DaoException;

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	public abstract <T> T findEqualSingle(Class<T> entityClass, Object paramObj)
			throws DaoException;

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	public abstract Object findEqualSingle(Object entity, String[] ignores)
			throws DaoException;

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 查询单条记录, 如果查询到多条抛出异常
	 */
	public abstract Object findEqualSingle(Object entity) throws DaoException;

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
	public abstract <T> List<T> findEqual(Class<T> entityClass,
			Object paramObj, Map<String, String> mapping, String[] ignores,
			int firstResult, int maxResults);

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 */
	public abstract <T> List<T> findEqual(Class<T> entityClass,
			Object paramObj, Map<String, String> mapping, int firstResult,
			int maxResults);

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 */
	public abstract <T> List<T> findEqual(Class<T> entityClass,
			Object paramObj, String[] ignores, int firstResult, int maxResults);

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 */
	public abstract <T> List<T> findEqual(Class<T> entityClass,
			Object paramObj, int firstResult, int maxResults);

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 */
	public abstract List<?> findEqual(Object entity, String[] ignores,
			int firstResult, int maxResults);

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 */
	public abstract List<?> findEqual(Object entity, int firstResult,
			int maxResults);

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	public abstract <T> List<T> findEqual(Class<T> entityClass,
			Object paramObj, Map<String, String> mapping, String[] ignores);

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	public abstract <T> List<T> findEqual(Class<T> entityClass,
			Object paramObj, Map<String, String> mapping);

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	public abstract <T> List<T> findEqual(Class<T> entityClass,
			Object paramObj, String[] ignores);

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	public abstract <T> List<T> findEqual(Class<T> entityClass, Object paramObj);

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	public abstract List<?> findEqual(Object entity, String[] ignores);

	/**
	 * {@link RoofDaoSupport#findEqual(Class, Object, Map, String[], int, int)}
	 * 不带分页
	 */
	public abstract List<?> findEqual(Object entity);

	/**
	 * 更新一个持久化对象,并且绑定到当前Hibernate {@link org.hibernate.Session}
	 * 
	 * @param entity
	 *            需要更新的持久化对象
	 * @throws org.springframework.dao.DataAccessException
	 *             Hibernate errors 产生时抛出
	 * @see org.hibernate.Session#update(Object)
	 */
	public abstract void update(Object entity);

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
	public abstract Object updateIgnoreNull(Object entity, Serializable id);

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
	public abstract Object updateIgnoreNull(Object entity, Serializable id,
			String[] props);

	public abstract void saveOrUpdateIgnoreNull(Object entity);

	/**
	 * 更新对象忽略对象内的空值<br />
	 * <b>注意</b> 所有的属性必须使用对象类型 ,如果为原生类型将被忽略无法更新
	 * 
	 * @param entity
	 *            需要更新的持久化对象
	 */
	public abstract Object updateIgnoreNull(Object entity);

	/**
	 * 判断一个类是否是实体类
	 * 
	 * @param cls
	 *            需要判断的类
	 * @return 是否为实体类
	 */
	public abstract boolean isEntity(Class<?> cls);

	/**
	 * 将实体类id为null的属性替换成null防止报对象未持久化的异常
	 * 
	 * @param entity
	 *            需要替换的实体类
	 */
	public abstract void replaceEmptyToNull(Object entity);

	/**
	 * 读取实体类的Id值
	 * 
	 * @param entity
	 *            需要读取的实体类
	 * @return 主键值
	 */
	public abstract Serializable getPrimaryKey(Object entity);

	/**
	 * 获得主键property属性
	 * 
	 * @param <T>
	 * 
	 * @param entity
	 *            需要读取的实体类
	 * @return property属性
	 */
	public abstract <T> PropertyDescriptor getPrimaryKeyProperty(
			Class<T> entityClass);

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
	public abstract void saveOrUpdate(Object entity);

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
	public abstract void saveOrUpdateAll(Collection<?> entities);

	/**
	 * 更具持久化对象的主键保存或者更新对象集合.绑定对象到当前的Hibernate {@link org.hibernate.Session}.
	 * 保存时会忽略null值
	 * 
	 * @see {@link #updateIgnoreNull(Object)}
	 * @param entities
	 *            需要保存或者更新的持久化对象集合(将会绑定到当前的Hibernate <code>Session</code>)
	 */
	public abstract void updateAllIgnoreNull(Collection<?> entities);

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
	public abstract List<?> selectByValueBean(String queryString,
			Object valueBean);

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
	public abstract List<?> selectForList(String statementName);

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
	public abstract List<?> selectForList(String statementName,
			Object parameterObject);

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
	public abstract List<?> selectForList(String statementName,
			int skipResults, int maxResults);

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
	public abstract List<?> selectForList(String statementName,
			Object parameterObject, int skipResults, int maxResults);

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
	public abstract Object selectForObject(String statementName);

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
	public abstract Object selectForObject(String statementName,
			Object parameterObject);

	/**
	 * 执行一个映射的SQL UPDATE语句 ,Update同样可以用于其他的更新类型如insert和delete. 返回影响记录的行数 *
	 * <p/>
	 * UPDATE 语句不接收参数
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @return 返回影响记录的行数
	 */
	public abstract int update(String statementName);

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
	public abstract int update(String statementName, Object parameterObject);

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
	public abstract void update(String statementName, Object parameterObject,
			int requiredRowsAffected);

	/**
	 * 执行一个映射的SQL INSERT语句, 返回产生的主键
	 * <p/>
	 * INSERT 语句不接收参数
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @return 产生的主键
	 */
	public abstract Object save(String statementName);

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
	public abstract Object save(String statementName, Object parameterObject);

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
	public abstract int delete(String statementName);

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
	public abstract int delete(String statementName, Object parameterObject);

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
	public abstract void delete(String statementName, Object parameterObject,
			int requiredRowsAffected);

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
	public abstract List<Map<String, Object>> selectForCamelCaseMap(
			String statementName) throws DaoException;

	// ////////////////////////////////////////////////////////////////////////////////
	public abstract List<Map<String, Object>> selectForMap(String sql);

	public abstract List<Map<String, Object>> selectForMap(String sql,
			Object value);

	public abstract List<Map<String, Object>> selectForMap(String sql,
			Object... values);

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
	public abstract List<?> findByMappedQuery(String statementName,
			int firstResult, int maxResults);

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
	public abstract List<?> findByMappedQuery(String statementName,
			int firstResult, int maxResults, Object parameterObject);

	/**
	 * 执行一个映射的HQL SELECT语句, 返回一个结果集合
	 * <p/>
	 * SELECT 语句不接收参数
	 * 
	 * @param statementName
	 *            需要执行语句的名称
	 * @return 查询语句返回的结果
	 */
	public abstract List<?> findByMappedQuery(String statementName);

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
	public abstract List<?> findByMappedQuery(String statementName,
			Object parameterObject);

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
	public abstract Object findByMappedQuerySingle(String statementName)
			throws DaoException;

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
	public abstract Object findByMappedQuerySingle(String statementName,
			Object parameterObject) throws DaoException;

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
	public abstract Object executeByMappedQuery(String statementName);

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
	public abstract Object executeByMappedQuery(String statementName,
			Object parameterObject);

}