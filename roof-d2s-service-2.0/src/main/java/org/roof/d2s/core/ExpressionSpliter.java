package org.roof.d2s.core;

/**
 * url表达式拆分
 * 
 * @author liuxin
 *
 */
public interface ExpressionSpliter {
	/**
	 * 将一个url表达式拆分为url列表
	 * 
	 * @param url
	 * @return
	 */
	String[] split(String url);
}
