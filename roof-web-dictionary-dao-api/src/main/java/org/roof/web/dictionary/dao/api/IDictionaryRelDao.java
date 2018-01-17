package org.roof.web.dictionary.dao.api;

import java.io.Serializable;
import java.util.List;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;
import org.roof.web.dictionary.entity.DictionaryRel;

public interface IDictionaryRelDao extends IDaoSupport {

	Page page(Page page, DictionaryRel dictionaryRel);

	/**
	 * 根据实体类名和实体类ID 查询字典值
	 * 
	 * @param className
	 *            实体类名
	 * @param entityId
	 *            实体类ID
	 * @return
	 */
	List<DictionaryRel> selectByClassName(String className, Serializable relId);

	/**
	 * 根据表名和表ID查询字典值
	 * 
	 * @param tableName
	 *            表名
	 * @param tableId
	 *            表id
	 * @return
	 */
	List<DictionaryRel> selectByTableName(String tableName, Serializable relId);

	/**
	 * 删除关联的字典表值
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
	int deleteRel(String className, String tableName, String propertyName, Serializable relId);

	public abstract Serializable save(DictionaryRel entity);

}
