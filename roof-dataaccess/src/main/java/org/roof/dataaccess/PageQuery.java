package org.roof.dataaccess;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanGenerator;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.log4j.Logger;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.IPageQuery;
import org.roof.roof.dataaccess.api.Page;
import org.springframework.util.Assert;

/**
 * 分页查询
 * 
 * @author liuxin 2011-9-19
 * @version 1.0 PageQuery.java liuxin 2011-9-19
 */
public class PageQuery implements IPageQuery {

	private static final Logger LOG = Logger.getLogger(PageQuery.class);

	protected static final String _LAST_ROW_NUM = "lastrownum";

	protected static final String _LIMIT = "limit";

	protected static final String _FIRST_ROW_NUM = "firstrownum";

	protected Page page;

	protected String countStr;
	protected String queryStr;

	protected IDaoSupport daoSupport;

	public PageQuery(IDaoSupport daoSupport) {
		super();
		this.daoSupport = daoSupport;
	}

	/**
	 * 创建一个分页查询<br>
	 * 默认分页数20
	 * 
	 * @param start
	 *            开始记录数
	 * @param queryStr
	 *            查询语句或者XML映射的查询语句名称
	 * @param countStr
	 *            记录总数查询语句或者XML映射的记录总数查询语句名称
	 */
	public PageQuery(Long start, String queryStr, String countStr) {
		this(new Page(start), queryStr, countStr);
	}

	/**
	 * 创建一个分页查询<br>
	 * 
	 * @param start
	 *            开始记录数
	 * 
	 * @param limit
	 *            分页记录数
	 * @param queryStr
	 *            查询语句或者XML映射的查询语句名称
	 * @param countStr
	 *            记录总数查询语句或者XML映射的记录总数查询语句名称
	 */
	public PageQuery(Long start, Long limit, String queryStr, String countStr) {
		this(new Page(start, limit), queryStr, countStr);
	}

	/**
	 * 创建分页查询
	 * 
	 * @param page
	 *            分页对象
	 * @param queryStr
	 *            查询语句或者XML映射的查询语句名称
	 * @param countStr
	 *            记录总数查询语句或者XML映射的记录总数查询语句名称
	 */
	public PageQuery(Page page, String queryStr, String countStr) {
		Assert.notNull(page, "page 对象不能为空!");
		Assert.hasText(countStr, "记录总数查询语句不能为空!");
		Assert.hasText(queryStr, "查询语句不能为空!");
		this.page = page;
		this.countStr = countStr;
		this.queryStr = queryStr;
	}

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
	@Override
	public Page find(Object... values) {
		Long rowCount = (Long) daoSupport.executeForObject(countStr, values);
		page.setTotal(rowCount);
		List<?> rows = daoSupport.findForList(queryStr, page.getStart().intValue(), page.getLimit().intValue());
		page.setDataList(rows);
		return page;
	}

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
	@Override
	public Page findByMappedQuery(Object parameterObject) {
		Long rowCount = (Long) daoSupport.executeByMappedQuery(countStr, parameterObject);
		page.setTotal(rowCount);
		List<?> rows = daoSupport.findByMappedQuery(queryStr, page.getStart().intValue(), page.getLimit().intValue(),
				parameterObject);
		page.setDataList(rows);
		return page;
	}

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
	@Override
	public Page select(Object parameterObject) {
		return this.select(parameterObject, false);
	}

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
	@Override
	@Deprecated
	public Page select(Object parameterObject, boolean autoPage) {
		if (parameterObject == null) {
			throw new IllegalArgumentException("分页查询参数对象不能为空!");
		}
		Long rowCount = 0L;
		List<?> rows = null;
		if (autoPage) {
			rowCount = (Long) daoSupport.selectForObject(countStr, parameterObject);
			rows = daoSupport.selectForList(queryStr, page.getStart().intValue(), page.getLimit().intValue());
		} else {
			long firstrownum = page.getStart();
			long limit = page.getLimit();
			long lastrownum = firstrownum + limit;
			Map<String, Object> addons = new HashMap<String, Object>();
			addons.put(_FIRST_ROW_NUM, firstrownum);
			addons.put(_LIMIT, limit);
			addons.put(_LAST_ROW_NUM, lastrownum);
			Object paramObj = addProperties(parameterObject, addons);
			rowCount = (Long) daoSupport.selectForObject(countStr, paramObj);
			rows = daoSupport.selectForList(queryStr, paramObj);
		}
		page.setTotal(rowCount);
		page.setDataList(rows);
		return page;
	}

	protected Object addProperties(Object parameterObject, Map<String, Object> addons) {
		if (LOG.isInfoEnabled()) {
			LOG.info("添加分页参数：" + addons);
		}
		if (ClassUtils.isAssignable(parameterObject.getClass(), Map.class)) {
			Map<String, Object> params = (Map<String, Object>) parameterObject;
			params.putAll(addons);
			return params;
		} else {
			BeanGenerator beanGenerator = new BeanGenerator();
			beanGenerator.setSuperclass(parameterObject.getClass());
			for (Entry<String, Object> entry : addons.entrySet()) {
				Class<?> cls = null;
				if (entry.getValue() == null) {
					cls = Object.class;
				} else {
					cls = entry.getValue().getClass();
				}
				beanGenerator.addProperty(entry.getKey(), cls);
			}
			Object o = beanGenerator.create();
			BeanCopier beanCopier = BeanCopier.create(parameterObject.getClass(), o.getClass(), false);
			beanCopier.copy(parameterObject, o, null);
			for (Entry<String, Object> entry : addons.entrySet()) {
				try {
					PropertyUtils.setProperty(o, entry.getKey(), entry.getValue());
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					LOG.error(e.getMessage(), e);
				}
			}
			return o;
		}
	}

	// 参数对象为Map在Map中添加分页属性
	@SuppressWarnings("unchecked")
	@Deprecated
	protected Object addMapProperties(Object parameterObject, long start, long limit, long end) {
		Object paramObj;
		@SuppressWarnings("rawtypes")
		Map params = (Map) parameterObject;
		params.put(_FIRST_ROW_NUM, start);
		params.put(_LIMIT, limit);
		params.put(_LAST_ROW_NUM, end);
		paramObj = params;
		return paramObj;
	}

	// 参数为JavaBean动态为JavaBean生成子类添加属性
	@Deprecated
	protected Object createParamObj(Object parameterObject, long start, long limit, long end) {
		BeanGenerator beanGenerator = new BeanGenerator();
		beanGenerator.setSuperclass(parameterObject.getClass());
		beanGenerator.addProperty(_FIRST_ROW_NUM, Long.class);
		beanGenerator.addProperty(_LIMIT, Long.class);
		beanGenerator.addProperty(_LAST_ROW_NUM, Long.class);
		Object o = beanGenerator.create();
		BeanCopier beanCopier = BeanCopier.create(parameterObject.getClass(), o.getClass(), false);
		beanCopier.copy(parameterObject, o, null);
		try {
			PropertyUtils.setProperty(o, _FIRST_ROW_NUM, start);
			PropertyUtils.setProperty(o, _LIMIT, limit);
			PropertyUtils.setProperty(o, _LAST_ROW_NUM, end);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			LOG.error(e.getMessage(), e);
		}
		return o;
	}

	public IDaoSupport getDaoSupport() {
		return daoSupport;
	}

	public void setDaoSupport(IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

	@Override
	public Page getPage() {
		return page;
	}

	@Override
	public void setPage(Page page) {
		this.page = page;
	}

	@Override
	public String getCountStr() {
		return countStr;
	}

	@Override
	public String getQueryStr() {
		return queryStr;
	}

	@Override
	public void setCountStr(String countStr) {
		this.countStr = countStr;
	}

	@Override
	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}

}
