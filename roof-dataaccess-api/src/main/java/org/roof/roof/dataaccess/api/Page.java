package org.roof.roof.dataaccess.api;

import java.io.Serializable;
import java.util.Collection;

/**
 * 分页
 * 
 * @author liuxin 2011-9-10
 * @version 1.0 Page.java liuxin 2011-9-10
 */
public class Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1891808330846602745L;
	public static final long DEFAULT_LIMIT = 10L;
	public static final long DEFAULT_START = 0L;

	protected Long total = 0L;
	protected Long start = 0L;
	protected Long limit = DEFAULT_LIMIT;

	private Collection<?> dataList;

	public Page() {
	}

	public Page(Long start) {
		this(start, DEFAULT_LIMIT);
	}

	public Page(Long start, Long limit) {
		super();
		this.start = start;
		this.limit = limit;
	}

	/**
	 * 记录总数
	 * 
	 * @return
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * 开始的记录
	 * 
	 * @return
	 */
	public Long getStart() {
		return start;
	}

	/**
	 * 分页记录数
	 * 
	 * @return
	 */
	public Long getLimit() {
		return limit;
	}

	/**
	 * 结果集
	 * 
	 * @return
	 */
	public Collection<?> getDataList() {
		return dataList;
	}

	/**
	 * 当前的页
	 * 
	 * @return
	 */
	public Long getCurrentPage() {
		return (start / limit) + 1;
	}

	public void setCurrentPage(Long currentPage) {
		if (currentPage == 0) {
			currentPage = 1L;
		}
		this.start = (currentPage - 1) * this.limit;
	}

	/**
	 * 总页数
	 * 
	 * @return
	 */
	public Long getPageCount() {
		if (total == 0) {
			return 0L;
		}
		if (total % limit == 0) {
			return total / limit;
		}
		return (total / limit) + 1;
	}

	/**
	 * 下一页
	 * 
	 * @return 是否可以跳转到下一页
	 */
	public boolean nextPage() {
		if (this.total <= 0) {
			return false;
		}
		Long nextFirstResult = this.start + this.limit;
		if (nextFirstResult <= this.total) {
			this.start = nextFirstResult;
			return true;
		}
		return false;
	}

	/**
	 * 上一页
	 * 
	 * @return 是否可以跳转到上一页
	 */
	public boolean prePage() {
		if (this.total <= 0) {
			return false;
		}
		Long nextFirstResult = this.start - this.limit;
		if (nextFirstResult >= 0) {
			this.start = nextFirstResult;
			return true;
		}
		return false;

	}

	/**
	 * 跳转页数
	 * 
	 * @param number
	 *            要跳转的页数
	 * 
	 * @return 是否可以跳转到指定的页
	 */
	public boolean gotoPage(Long number) {
		return false;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public void setDataList(Collection<?> dataList) {
		this.dataList = dataList;
	}

}
