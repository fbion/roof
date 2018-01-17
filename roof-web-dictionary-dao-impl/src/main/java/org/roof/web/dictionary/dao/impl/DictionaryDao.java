package org.roof.web.dictionary.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.portable.ApplicationException;
import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.DaoException;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.web.dictionary.dao.api.IDictionaryDao;
import org.roof.web.dictionary.entity.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 字典表 Dao
 * 
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 DictionaryDao.java 2011-11-4
 */
@Service
public class DictionaryDao extends AbstractDao implements IDictionaryDao {

	@Override
	public Serializable save(Object entity) {
		return daoSupport.save(entity);
	}

	public Dictionary load(Long id) {
		return (Dictionary) daoSupport.reload(new Dictionary((Long) id));
	}

	@Override
	public List<Dictionary> findByType(String type) {
		Dictionary dictionary = new Dictionary();
		dictionary.setType(type);
		dictionary.setActive("1");
		@SuppressWarnings("unchecked")
		List<Dictionary> dictionaries = (List<Dictionary>) daoSupport
				.selectForList(
						"org.roof.web.dictionary.dao.impl.DictionaryDao.query",
						dictionary);
		return dictionaries;
	}

	@Override
	public Long findChildrenCount(String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", type);
		return (Long) daoSupport
				.selectForObject(
						"org.roof.web.dictionary.dao.impl.DictionaryDao.findChildrenCount",
						param);
	}

	/**
	 * 获得字典表对象
	 * 
	 * @param type
	 *            类型
	 * @param val
	 *            值
	 * @return 值对象
	 * @throws ApplicationException
	 *             当存在着相同的(类型+值)的时候抛出
	 */
	@Override
	public Dictionary load(String type, String val) throws DaoException {
		Assert.hasText(val);
		Assert.hasText(type);
		Dictionary dictionary = new Dictionary();
		dictionary.setVal(val);
		dictionary.setType(type);
		dictionary = (Dictionary) daoSupport.selectForObject(
				"org.roof.web.dictionary.dao.impl.DictionaryDao.queryEq",
				dictionary);
		return dictionary;
	}

	@Override
	public Dictionary loadByTypeText(String type, String text)
			throws DaoException {
		Assert.hasText(text);
		Assert.hasText(type);
		Dictionary dictionary = new Dictionary();
		dictionary.setText(text);
		dictionary.setType(type);
		dictionary = (Dictionary) daoSupport.selectForObject(
				"org.roof.web.dictionary.dao.impl.DictionaryDao.queryEq",
				dictionary);
		return dictionary;
	}

	@Override
	public List<Dictionary> query(String type, String val, String text,
			String active) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("val", val);
		map.put("text", text);
		map.put("active", active);
		@SuppressWarnings("unchecked")
		List<Dictionary> result = (List<Dictionary>) daoSupport.findForList(
				"org.roof.web.dictionary.dao.impl.DictionaryDao.query", map);
		return result;
	}

	@Override
	public List<Dictionary> queryEq(String type, String val, String text,
			String active) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("val", val);
		map.put("text", text);
		map.put("active", active);
		@SuppressWarnings("unchecked")
		List<Dictionary> result = (List<Dictionary>) daoSupport.findForList(
				"org.roof.web.dictionary.dao.impl.DictionaryDao.queryEq", map);
		return result;
	}

	@Autowired
	public void setDaoSupport(
			@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

}