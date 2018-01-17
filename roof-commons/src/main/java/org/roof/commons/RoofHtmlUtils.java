package org.roof.commons;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * Html转义
 * 
 * @see HtmlUtils
 * 
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 RoofHtmlUtils.java 2013-3-13
 */
public abstract class RoofHtmlUtils {
	public static final String DECIMAL = "Decimal";
	public static final String HEX = "Hex";
	public static final String UNESCAPE = "UNESCAPE";

	public static String delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		return htmlStr.trim(); // 返回文本字符串
	}

	public static Object htmlEscape(Object input) {
		return htmlEscape(input, null);
	}

	public static Object htmlEscape(Object input, String type) {
		if (input == null || input instanceof Class) {
			return null;
		}
		if (input instanceof String) {
			return htmlEscapeString((String) input, type);
		}
		if (RoofBeanUtils.isSimple(input.getClass())) {
			return input;
		}
		if (ClassUtils.isAssignable(input.getClass(), Collection.class)) {
			return input;
		}
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(input);
		try {
			for (PropertyDescriptor pd : pds) {
				Object o = PropertyUtils.getProperty(input, pd.getName());
				Object escaped = htmlEscape(o, type);
				if (escaped != null && escaped instanceof String) {
					PropertyUtils.setProperty(input, pd.getName(), escaped);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return input;
	}

	private static String htmlEscapeString(String input, String type) {
		if (type == null) {
			return HtmlUtils.htmlEscape(input);
		}
		if (StringUtils.equals(HEX, type)) {
			return HtmlUtils.htmlEscapeHex(input);
		}
		if (StringUtils.equals(DECIMAL, type)) {
			return HtmlUtils.htmlEscapeDecimal(input);
		}
		if (StringUtils.equals(UNESCAPE, type)) {
			return HtmlUtils.htmlUnescape(input);
		}
		throw new IllegalArgumentException("type 未定义!");
	}

	public static Object htmlUnescape(Object input) {
		return htmlEscape(input, UNESCAPE);
	}

}
