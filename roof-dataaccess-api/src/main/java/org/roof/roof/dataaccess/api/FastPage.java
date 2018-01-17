package org.roof.roof.dataaccess.api;

import java.io.Serializable;

/**
 * 优化分页
 * 
 * @author liuxin
 *
 */
public class FastPage extends Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4023420791480957384L;
	private static final String ORDER_BY_PROPERTY_NAME_ID = "id";
	public static final String ODER_BY_TYPE_ASC = "asc";
	public static final String ODER_BY_TYPE_DESC = "desc";

	private Object orderByPropertyStart = 0; // 分页排序字段开始值
	private Object orderByPropertyEnd = 0;// 分页排序字段结束值
	private String orderByPropertyName = ORDER_BY_PROPERTY_NAME_ID; // 分页排序字段名称
	private String orderByType = ODER_BY_TYPE_DESC; // 分页排序类型

	private long nextStart;

	public void reset() {
		this.setCurrentPage(0L);
		this.setOrderByPropertyStart(0L);
		this.setOrderByPropertyEnd(0L);
		this.setNextPage(0L);
	}

	public void setNextPage(long nextPage) {
		if (nextPage == 0) {
			nextPage = 1L;
		}
		this.nextStart = (nextPage - 1) * this.limit;
	}

	public Long getNextPage() {
		return (this.nextStart / limit) + 1;

	}

	public Object getOrderByPropertyStart() {
		return orderByPropertyStart;
	}

	public void setOrderByPropertyStart(Object orderByPropertyStart) {
		this.orderByPropertyStart = orderByPropertyStart;
	}

	public Object getOrderByPropertyEnd() {
		return orderByPropertyEnd;
	}

	public void setOrderByPropertyEnd(Object orderByPropertyEnd) {
		this.orderByPropertyEnd = orderByPropertyEnd;
	}

	public String getOrderByPropertyName() {
		return orderByPropertyName;
	}

	public void setOrderByPropertyName(String orderByPropertyName) {
		this.orderByPropertyName = orderByPropertyName;
	}

	public long getNextStart() {
		return nextStart;
	}

	public void setNextStart(long nextStart) {
		this.nextStart = nextStart;
	}

	public String getOrderByType() {
		return orderByType;
	}

	public void setOrderByType(String orderByType) {
		this.orderByType = orderByType;
	}

}
