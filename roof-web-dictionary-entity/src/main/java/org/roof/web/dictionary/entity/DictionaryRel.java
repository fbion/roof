package org.roof.web.dictionary.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 字典表引用关系
 * 
 * @author liuxin
 *
 */
@Entity
@Table(name = "s_dictionary_rel")
public class DictionaryRel implements Serializable {

	private static final long serialVersionUID = 6210311268338986823L;
	public static final String DEFAULT_THIS_TABLE_NAME = "s_dictionary_rel";
	protected Long id;
	protected String className; // 关联的类名
	protected String tableName; // 关联的表名
	protected String propertyName; // 属性名称
	protected Long entityId; // 引用的实体类Id
	protected Long dicId; // 字典表Id

	protected String thisTableName = DEFAULT_THIS_TABLE_NAME;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Long getDicId() {
		return dicId;
	}

	public void setDicId(Long dicId) {
		this.dicId = dicId;
	}

	public String getThisTableName() {
		return thisTableName;
	}

	public void setThisTableName(String thisTableName) {
		this.thisTableName = thisTableName;
	}

}
