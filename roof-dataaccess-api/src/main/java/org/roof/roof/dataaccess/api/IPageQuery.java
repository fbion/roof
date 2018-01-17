package org.roof.roof.dataaccess.api;

public interface IPageQuery {

	/**
	 * 通过HQL语句进行分页查询
	 * <p/>
	 * <b>HQL查询语句中无需使用分页,创建PageQuery对象时queryStr和countStr参数必须为HQL语句<b/>
	 * 
	 * @param values
	 *            参数数组(对应HQL中的占位符(?))
	 * 
	 * @return 分页对象
	 */
	public abstract Page find(Object... values);

	/**
	 * 通过XML映射的HQL语句分页查询
	 * <p/>
	 * <b>HQL查询语句中无需使用分页,创建PageQuery对象时queryStr和countStr参数必须为XML中映射的HQL语句名称<b/>
	 * 
	 * @param parameterObject
	 *            参数对象
	 * 
	 * @return 分页对象
	 */
	public abstract Page findByMappedQuery(Object parameterObject);

	/**
	 * 通过XML映射的SQL语句分页查询
	 * 
	 * HQL查询语句中必须使用分页,创建PageQuery对象时queryStr和countStr参数必须为XML中映射的SQL语句名称<br />
	 * 方法会将一下参数带入SQL <br />
	 * <ul>
	 * <li>1.firstrownum 查询开始的记录行</li>
	 * <li>2.limit 每页最大的记录行数</li>
	 * <li>3.lastrownum 查询结束的记录行</li>
	 * </ul>
	 * 
	 * @param parameterObject
	 *            参数对象
	 * @return 分页对象
	 */
	public abstract Page select(Object parameterObject);

	/**
	 * 通过XML映射的SQL语句分页查询
	 * 
	 * HQL查询语句中必须使用分页,创建PageQuery对象时queryStr和countStr参数必须为XML中映射的SQL语句名称<br />
	 * 方法会将一下参数带入SQL进行查询 <br />
	 * <ul>
	 * <li>1.firstrownum 查询开始的记录行</li>
	 * <li>2.limit 每页最大的记录行数</li>
	 * <li>3.lastrownum 查询结束的记录行</li>
	 * </ul>
	 * 
	 * <b> 注意:使用该分页查询 参数对象(parameterObject)不能为空
	 * 
	 * @param parameterObject
	 *            参数对象
	 * @param autoPage
	 *            是否采用Ibatis自动分页(<b>注意:效率较低)
	 * @return 分页对象
	 */
	public abstract Page select(Object parameterObject, boolean autoPage);

	public abstract Page getPage();

	public abstract void setPage(Page page);

	public abstract String getCountStr();

	public abstract String getQueryStr();

	public abstract void setCountStr(String countStr);

	public abstract void setQueryStr(String queryStr);

	public void setDaoSupport(IDaoSupport daoSupport);

}