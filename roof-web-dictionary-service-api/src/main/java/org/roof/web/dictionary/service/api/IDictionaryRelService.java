package org.roof.web.dictionary.service.api;

import java.io.Serializable;

public interface IDictionaryRelService {
	/**
	 * 保存实体类与字典表的关联关系
	 * 
	 * @param entity
	 *            实体类
	 */
	Object saveRel(Object entity);
	
	Object saveRel(Object entity,Boolean del);

	/**
	 * 删除实体类与字典表的关联关系
	 * 
	 * @param className
	 *            类全名
	 * @param tableName
	 *            表名
	 * @param propertyName
	 *            属性id
	 * @param relId
	 *            实体类Id
	 * @return 删除的记录行数
	 */
	int deleteRel(String className, String tableName, String propertyName, Serializable entityId);

	/**
	 * 填充实体类的字典值
	 * 
	 * @param entity
	 *            实体类
	 */
	Object fill(Object entity);

	/**
	 * 填充VO(值对象)的字典值
	 * 
	 * @param obj
	 *            VO(值对象)
	 * @param cls
	 *            实体类
	 * @param entityId
	 *            实体类Id
	 */
	Object fill(Object obj, Class<?> cls, Serializable entityId);

	/**
	 * 填充VO(值对象)的字典值
	 * 
	 * @param obj
	 *            VO(值对象)
	 * @param tableName
	 *            表名
	 * @param tableId
	 *            表id
	 */
	Object fill(Object obj, String tableName, Serializable tableId);

}
