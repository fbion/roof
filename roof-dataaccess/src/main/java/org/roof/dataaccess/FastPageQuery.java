package org.roof.dataaccess;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.roof.roof.dataaccess.api.FastPage;
import org.roof.roof.dataaccess.api.IDaoSupport;

/**
 * 必须设置当前页{@link FastPage#setCurrentPage(Long)}和目标页
 * {@link FastPage#setNextPage(long)}<br/>
 * 当前页可以不设置，默认为0，此时如果查询分页页码过大，会导致效率低下<br />
 * 设置当前页的同时必须设置排序字段当前页的开始值{@link FastPage#setOrderByPropertyStart(Object)}和结束值
 * {@link FastPage#setOrderByPropertyEnd(Object)}，<b>且必须设置正确，否则分页错误</b><br />
 * 
 * 当分页总数查询语句{@link FastPageQuery#setCountStr(String)} 为空，或者分页总数值
 * {@link FastPage#setTotal(Long)}不为0时<b>不会查询分页总数据，提高查询效率</b>，此时默认100页<br />
 * 
 * 查询语句模板 <code>
		select t1.*
		from table t1 join
		(select id from t_car
			where 1=1
			<if test="symbol != null and orderByVal != null">
				and id ${symbol} #{orderByVal}
			</if>
			(.......查询条件)
		order by id ${orderByType}
		limit #{skipRow}, #{limit}) t2
		where t1.id = t2.id
 * </code>
 * 
 * @author liuxin
 *
 */
public class FastPageQuery extends PageQuery {

	private static final String _ORDER_BY_TYPE = "orderByType";

	private static final Logger LOG = Logger.getLogger(FastPageQuery.class);

	private static final String _ORDER_BY_VAL = "orderByVal";

	private static final String _SYMBOL = "symbol";

	private static final String _SKIP_ROW = "skipRow";

	protected String symbol = null;
	protected Object orderByVal = null;
	protected String orderByType = null;
	protected boolean needReverse = false;
	protected FastPage fastPage = null;
	protected long firstrownum;
	protected long limit;
	protected long skipRow;
	protected long lastrownum;

	public FastPageQuery(IDaoSupport daoSupport) {
		super(daoSupport);
	}

	@Override
	public FastPage select(Object parameterObject) {
		if (page instanceof FastPage) {
			fastPage = (FastPage) page;
		} else {
			throw new IllegalArgumentException("FastPageQuery 必须使用 FastPage");
		}
		Long rowCount = 0L;
		if (StringUtils.isNotEmpty(countStr)) {
			rowCount = (Long) daoSupport.selectForObject(countStr,
					parameterObject);
		} else {
			rowCount = 100 * limit;
		}
		fastPage.setTotal(rowCount);
		if (fastPage.getPageCount() < fastPage.getNextPage()) {
			fastPage.reset();
		}
		List<?> rows = null;
		initParams();

		orderByType = fastPage.getOrderByType();
		if (FastPage.ODER_BY_TYPE_ASC.equals(orderByType)) {
			handleAsc(); // 处理正序
		} else {
			handleDesc(); // 处理倒序
		}
		Object paramObj = addPageParams(parameterObject);// 添加分页参数

		rows = daoSupport.selectForList(queryStr, paramObj);
		if (needReverse) {
			Collections.reverse(rows);
		}
		if (CollectionUtils.isNotEmpty(rows)) {
			try {
				fastPage.setOrderByPropertyStart(PropertyUtils.getProperty(
						rows.get(0), fastPage.getOrderByPropertyName()));
				fastPage.setOrderByPropertyEnd(PropertyUtils.getProperty(
						rows.get(rows.size() - 1),
						fastPage.getOrderByPropertyName()));
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		fastPage.setCurrentPage(fastPage.getNextPage());
		fastPage.setDataList(rows);
		return fastPage;
	}

	private void initParams() {
		firstrownum = fastPage.getNextStart();
		limit = fastPage.getLimit();
		skipRow = 0;
		lastrownum = firstrownum + limit;
	}

	private Object addPageParams(Object parameterObject) {
		Map<String, Object> addons = new HashMap<String, Object>();
		addons.put(_FIRST_ROW_NUM, firstrownum);
		addons.put(_LAST_ROW_NUM, lastrownum);
		addons.put(_LIMIT, limit);
		addons.put(_SKIP_ROW, skipRow);
		addons.put(_SYMBOL, symbol);
		addons.put(_ORDER_BY_TYPE, orderByType);
		addons.put(_ORDER_BY_VAL, orderByVal);
		Object paramObj = addProperties(parameterObject, addons);
		return paramObj;
	}

	private void handleDesc() {
		if (fastPage.getCurrentPage() < fastPage.getNextPage()) {
			symbol = "<";
			orderByVal = fastPage.getOrderByPropertyEnd();
			skipRow = Math.abs(fastPage.getNextStart() - fastPage.getStart())
					- limit;
		} else if (fastPage.getCurrentPage() == fastPage.getNextPage()) {
			if (fastPage.getCurrentPage() != 1L) {
				symbol = "<=";
				orderByVal = fastPage.getOrderByPropertyStart();
			}
		} else {
			symbol = ">";
			orderByVal = fastPage.getOrderByPropertyStart();
			skipRow = Math.abs(fastPage.getNextStart() - fastPage.getStart())
					- limit;
			orderByType = FastPage.ODER_BY_TYPE_ASC;
			needReverse = true;
		}
	}

	private void handleAsc() {
		if (fastPage.getCurrentPage() < fastPage.getNextPage()) {
			symbol = ">";
			orderByVal = fastPage.getOrderByPropertyEnd();
			skipRow = Math.abs(fastPage.getNextStart() - fastPage.getStart())
					- limit;
		} else if (fastPage.getCurrentPage() == fastPage.getNextPage()) {
			if (fastPage.getCurrentPage() != 1L) {
				symbol = ">=";
				orderByVal = fastPage.getOrderByPropertyStart();
			}
		} else {
			symbol = "<";
			orderByVal = fastPage.getOrderByPropertyStart();
			skipRow = Math.abs(fastPage.getNextStart() - fastPage.getStart())
					- limit;
			orderByType = FastPage.ODER_BY_TYPE_DESC;
			needReverse = true;
		}
	}
}
