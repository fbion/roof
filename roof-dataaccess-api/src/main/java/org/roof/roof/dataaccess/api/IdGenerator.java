package org.roof.roof.dataaccess.api;

import java.beans.PropertyDescriptor;
import java.io.Serializable;

/**
 * Id 生成器
 * 
 * @author liuxin
 *
 */
public interface IdGenerator {
	/**
	 * 设置并返回生成Id
	 * 
	 * @param entity
	 * @param idPropertyDescriptor
	 * @return
	 */
	Serializable setId(Object entity, PropertyDescriptor idPropertyDescriptor);

}
