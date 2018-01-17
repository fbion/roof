package org.roof.web.dictionary.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.roof.dataaccess.FastPageQuery;
import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.IPageQuery;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageQueryFactory;
import org.roof.spring.CurrentSpringContext;
import org.roof.web.dictionary.dao.api.IDictionaryRelDao;
import org.roof.web.dictionary.entity.DictionaryRel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DictionaryRelDao extends AbstractDao implements IDictionaryRelDao {
	
	private static final Logger LOGGER = Logger.getLogger(DictionaryRelDao.class);

	private Map<String, String> dictionaryRelMap;

	private PageQueryFactory<FastPageQuery> pageQueryFactory;

	public Page page(Page page, DictionaryRel dictionaryRel) {
		IPageQuery pageQuery = pageQueryFactory.getPageQuery(page, "selectDictionaryRelPage",
				"selectDictionaryRelCount");
		// IPageQuery pageQuery =
		// pageQueryFactory.getPageQuery(page,"selectDictionaryRelPage", null);
		return pageQuery.select(dictionaryRel);
	}
	
	@Override
	public Serializable save(DictionaryRel entity) {
		fillThisTableName(entity);
		return daoSupport.save(entity);
	}

	/**
	 * 根据实体类名和实体类ID 查询字典值
	 * 
	 * @param className
	 *            实体类名
	 * @param entityId
	 *            实体类ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DictionaryRel> selectByClassName(String className, Serializable entityId) {
		DictionaryRel parameterObject = new DictionaryRel();
		parameterObject.setClassName(className);
		parameterObject.setEntityId((Long) entityId);
		fillThisTableName(parameterObject);
		return (List<DictionaryRel>) selectForList("org.roof.web.dictionary.dao.DictionaryRelDao.selectDictionaryRel",
				parameterObject);
	}

	/**
	 * 根据表名和表ID查询字典值
	 * 
	 * @param tableName
	 *            表名
	 * @param tableId
	 *            表id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DictionaryRel> selectByTableName(String tableName, Serializable tableId) {
		DictionaryRel parameterObject = new DictionaryRel();
		parameterObject.setTableName(tableName);
		parameterObject.setEntityId((Long) tableId);
		fillThisTableName(parameterObject);
		return (List<DictionaryRel>) selectForList("org.roof.web.dictionary.dao.DictionaryRelDao.selectDictionaryRel",
				parameterObject);
	}

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
	@Override
	public int deleteRel(String className, String tableName, String propertyName, Serializable entityId) {
		DictionaryRel parameterObject = new DictionaryRel();
		parameterObject.setClassName(className);
		parameterObject.setTableName(tableName);
		parameterObject.setPropertyName(propertyName);
		parameterObject.setEntityId((Long) entityId);
		fillThisTableName(parameterObject);
		List<DictionaryRel> list = (List<DictionaryRel>) selectForList("org.roof.web.dictionary.dao.DictionaryRelDao.selectDictionaryRel",
				parameterObject);
		for (DictionaryRel dictionaryRel : list) {
			dictionaryRel.setThisTableName(parameterObject.getThisTableName());
			delete("org.roof.web.dictionary.dao.DictionaryRelDao.deleteDictionaryRel", dictionaryRel);
		}
		LOGGER.debug(list.size());
		return list.size();
//		return delete("org.roof.web.dictionary.dao.DictionaryRelDao.deleteByExampleDictionaryRel", parameterObject);
	}

	private void fillThisTableName(DictionaryRel dictionaryRel) {
		if (this.dictionaryRelMap == null) {
			dictionaryRelMap = CurrentSpringContext.getBean("dictionaryRelMap", Map.class);
		}
		if (this.dictionaryRelMap == null) {
			return;
		}
		String thisTableName = DictionaryRel.DEFAULT_THIS_TABLE_NAME;
		if (StringUtils.isNotBlank(dictionaryRel.getClassName())) {
			thisTableName = dictionaryRelMap.get(dictionaryRel.getClassName());
		}
		if (StringUtils.isNotBlank(thisTableName)) {
			dictionaryRel.setThisTableName(thisTableName);
			return;
		}
		if (StringUtils.isNotBlank(dictionaryRel.getTableName())) {
			thisTableName = dictionaryRelMap.get(dictionaryRel.getTableName());
		}
		if (StringUtils.isNotBlank(thisTableName)) {
			dictionaryRel.setThisTableName(thisTableName);
			return;
		}
	}

	@Autowired
	public void setDaoSupport(@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

	@Autowired
	public void setPageQueryFactory(
			@Qualifier("fastPageQueryFactory") PageQueryFactory<FastPageQuery> pageQueryFactory) {
		this.pageQueryFactory = pageQueryFactory;
	}

	public Map<String, String> getDictionaryRelMap() {
		return dictionaryRelMap;
	}

	public void setDictionaryRelMap(Map<String, String> dictionaryRelMap) {
		this.dictionaryRelMap = dictionaryRelMap;
	}

}