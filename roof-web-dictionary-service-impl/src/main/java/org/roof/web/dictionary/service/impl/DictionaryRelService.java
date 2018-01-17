package org.roof.web.dictionary.service.impl;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.log4j.Logger;
import org.roof.web.dictionary.dao.api.IDictionaryDao;
import org.roof.web.dictionary.dao.api.IDictionaryRelDao;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.entity.DictionaryRel;
import org.roof.web.dictionary.service.api.IDictionaryRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DictionaryRelService implements IDictionaryRelService {

	private IDictionaryRelDao dictionaryRelDao;
	private IDictionaryDao dictionaryDao;
	private static final Logger LOG = Logger.getLogger(DictionaryRelService.class);
	
	public Object saveRel(Object entity) {
		return saveRel(entity,true);
	}
	/**
	 * @see IDictionaryRelService#saveRel(Object)
	 */
	
	public Object saveRel(Object entity,Boolean del) {
		Assert.notNull(entity);
		Serializable entityId = getEntityId(entity);
		String tableName = getTableName(entity.getClass());
		String className = entity.getClass().getName();

		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(entity);

		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			try {
				Object val = propertyDescriptor.getReadMethod().invoke(entity);
				if (val == null) {
					continue;
				}
				Class<?> propertyType = propertyDescriptor.getPropertyType();
				if (val instanceof Dictionary) {
					Dictionary dic = (Dictionary) val;
					if(del){
						deleteRel(className, tableName, propertyDescriptor.getName(), entityId);
					}
					saveRel(entityId, tableName, className, propertyDescriptor, dic,del);
				}
				if (ClassUtils.isAssignable(propertyType, List.class)) {
					List<?> list = (List<?>) val;
					if(del){
						deleteRel(className, tableName, propertyDescriptor.getName(), entityId);
					}
					for (Object o : list) {
						if (o instanceof Dictionary) {
							Dictionary dic = (Dictionary) o;
							saveRel(entityId, tableName, className, propertyDescriptor, dic,del);
						} else {
							break;
						}
					}
				}

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		return entity;

	}

	/**
	 * @see IDictionaryRelService#deleteRel(String, String, String,
	 *      Serializable)
	 */
	@Override
	public int deleteRel(String className, String tableName, String propertyName, Serializable entityId) {
		return dictionaryRelDao.deleteRel(className, tableName, propertyName, entityId);
	}

	/*
	 * 保存关联关系
	 */
	private void saveRel(Serializable entityId, String tableName, String className,
			PropertyDescriptor propertyDescriptor, Dictionary dic,Boolean del) {
		String propertyName = propertyDescriptor.getName();
		if(del){
			dictionaryRelDao.deleteRel(className, propertyName, tableName, entityId);
		}
		DictionaryRel dictionaryRel = new DictionaryRel();
		dictionaryRel.setEntityId((Long) entityId);
		dictionaryRel.setClassName(className);
		dictionaryRel.setTableName(tableName);
		dictionaryRel.setPropertyName(propertyName);
		dictionaryRel.setDicId(dic.getId());
		dictionaryRelDao.save(dictionaryRel);
	}
	
	/*
	 * 获得表名
	 */
	private String getTableName(Class<? extends Object> cls) {
		Table table = cls.getAnnotation(Table.class);
		if (table != null) {
			return table.name();
		}
		return null;
	}

	/**
	 * @see IDictionaryRelService#fill(Object)
	 */
	@Override
	public Object fill(Object entity) {
		Assert.notNull(entity);
		Serializable entityId = getEntityId(entity);
		fill(entity, entity.getClass(), entityId);
		return entity;
	}

	private Serializable getEntityId(Object entity) {
		Serializable id = dictionaryRelDao.getPrimaryKey(entity);
		Assert.notNull(id, "[" + entity.getClass().getName() + "]不是实体类，实体类必须有id属性或者带有@javax.persistence.Id注解的属性");
		return id;
	}

	/**
	 * @see IDictionaryRelService#fill(Object, Class, Serializable)
	 */
	@Override
	public Object fill(Object obj, Class<?> cls, Serializable entityId) {
		Assert.notNull(obj);
		Assert.notNull(entityId);
		if (cls == null) {
			cls = obj.getClass();
		}
		List<DictionaryRel> dictionaries = dictionaryRelDao.selectByClassName(cls.getName(), entityId);
		for (DictionaryRel dicRel : dictionaries) {
			setProperty(obj, dicRel);
		}
		return obj;
	}

	/**
	 * @see IDictionaryRelService#fill(Object, String, Serializable)
	 */
	@Override
	public Object fill(Object obj, String tableName, Serializable entityId) {
		Assert.notNull(obj);
		Assert.notNull(entityId);
		Assert.notNull(tableName);
		List<DictionaryRel> dictionaries = dictionaryRelDao.selectByTableName(tableName, entityId);
		for (DictionaryRel dicRel : dictionaries) {
			setProperty(obj, dicRel);
		}
		return obj;
	}

	private void setProperty(Object obj, DictionaryRel dicRel) {
		try {
			Dictionary dic = dictionaryDao.load(Dictionary.class, dicRel.getDicId());
			PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(obj, dicRel.getPropertyName());
			if(propertyDescriptor == null){
				return;
			}
			if (propertyDescriptor.getPropertyType() == Dictionary.class) {
				propertyDescriptor.getWriteMethod().invoke(obj, dic);
			}
			if (propertyDescriptor.getPropertyType() == List.class) {
				@SuppressWarnings("unchecked")
				List<Dictionary> val = (List<Dictionary>) propertyDescriptor.getReadMethod().invoke(obj);
				if (val == null) {
					val = new ArrayList<>();
					propertyDescriptor.getWriteMethod().invoke(obj, val);
				}
				val.add(dic);
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Autowired
	public void setDictionaryRelDao(@Qualifier("dictionaryRelDao") IDictionaryRelDao dictionaryRelDao) {
		this.dictionaryRelDao = dictionaryRelDao;
	}

	@Autowired
	public void setDictionaryDao(@Qualifier("dictionaryDao") IDictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}

}
