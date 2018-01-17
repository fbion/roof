package org.roof.roof.dataaccess.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PageUtils {

	private static final Logger LOG = Logger.getLogger(PageUtils.class);

	private static Long DEFAULT_LIMIT;

	static {
		Properties prop = new Properties();
		Resource resource = new ClassPathResource("project.properties");
		InputStream in = null;
		try {
			in = resource.getInputStream();
			prop.load(in);
			DEFAULT_LIMIT = Long.parseLong(prop.getProperty("page.default_limit").trim());
			if (LOG.isInfoEnabled()) {
				LOG.info("加载默认分页数" + DEFAULT_LIMIT);
			}
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 创建Page工具栏 <br/>
	 * 返回Map中包含<br/>
	 * pageStart : 分页开始页码 <br/>
	 * pageEnd : 分页结束页码
	 * 
	 * @param page
	 * @return
	 */
	public static Map<String, Long> createPagePar(Page page) {
		Map<String, Long> result = new HashMap<String, Long>();
		long pageStart = 1;
		if (page.getCurrentPage() > 6L) {
			pageStart = page.getCurrentPage() - 5L;
		}
		long pageEnd = pageStart + 10L;
		if (pageEnd > page.getPageCount()) {
			pageEnd = page.getPageCount();
		}
		result.put("pageStart", pageStart);
		result.put("pageEnd", pageEnd);
		return result;
	}

	public static Page createPage(String prefix, HttpServletRequest request) {
		FastPage page = new FastPage();
		if (prefix == null) {
			prefix = "";
		} else {
			prefix += ".";
		}
		Long rowCount = pasLong(request.getParameter(prefix + "rowCount"));
		Long start = pasLong(request.getParameter(prefix + "start"));
		Long total = pasLong(request.getParameter(prefix + "total"));
		Long limit = pasLong(request.getParameter(prefix + "limit"));
		Long currentPage = pasLong(request.getParameter(prefix + "currentPage"));
		Long nextPage = pasLong(request.getParameter(prefix + "nextPage"));
		Object orderByPropertyStart = pasObject(request.getParameter(prefix + "orderByPropertyStart"));
		Object orderByPropertyEnd = pasObject(request.getParameter(prefix + "orderByPropertyEnd"));
		if (limit == null || limit == 0) {
			limit = DEFAULT_LIMIT;
		}
		if (rowCount != null) {
			page.setTotal(rowCount);
		}
		if (start != null) {
			page.setStart(start);
		}
		if (limit != null) {
			page.setLimit(limit);
		}
		if (currentPage != null) {
			page.setCurrentPage(currentPage);
		}
		if (nextPage != null) {
			page.setNextPage(nextPage);
		}
		if (orderByPropertyStart != null) {
			page.setOrderByPropertyStart(orderByPropertyStart);
		}
		if (orderByPropertyEnd != null) {
			page.setOrderByPropertyEnd(orderByPropertyEnd);
		}
		if (total != null) {
			page.setTotal(total);
		}
		return page;
	}

	private static Object pasObject(String s) {
		if (NumberUtils.isNumber(s)) {
			return pasLong(s);
		}
		return s;
	}

	private static Long pasLong(String s) {
		if (s == null || "".equals(s)) {
			return null;
		}
		return Long.parseLong(s);
	}

	/**
	 * 获取分页
	 * 
	 * @return 分页对象
	 */
	public static Page createPage(HttpServletRequest request) {
		return createPage(null, request);
	}

}
