package org.roof.web.dictionary.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.omg.CORBA.portable.ApplicationException;
import org.roof.roof.dataaccess.api.DaoException;
import org.roof.web.dictionary.dao.api.IDictionaryDao;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.entity.DictionaryVo;
import org.roof.web.dictionary.service.api.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DictionaryService implements IDictionaryService {
	private IDictionaryDao dictionaryDao;
	private Logger LOGGER = Logger.getLogger(DictionaryService.class);

	@Override
	public List<Dictionary> findByType(String type) {
		return dictionaryDao.findByType(type);
	}

	@Override
	public List<DictionaryVo> read(String type) {
		if (StringUtils.isEmpty(type)) {
			type = "DIC";
		}
		List<Dictionary> dictionaries = new ArrayList<Dictionary>();
		dictionaries = dictionaryDao.findByType(type);
		return this.copyDicList(dictionaries);
	}

	private List<DictionaryVo> copyDicList(List<Dictionary> dictionaries) {
		List<DictionaryVo> result = new ArrayList<DictionaryVo>();
		for (Dictionary dictionary : dictionaries) {
			DictionaryVo dictionaryVo = new DictionaryVo();
			copyProperties(dictionary, dictionaryVo);
			result.add(dictionaryVo);
		}
		return result;
	}

	@Override
	public List<DictionaryVo> readVal(String type, String val) {
		if (StringUtils.isEmpty(val)) {
			val = "S_DIC";
		}
		List<Dictionary> dictionaries = new ArrayList<Dictionary>();
		if (StringUtils.isEmpty(type)) {
			dictionaries = dictionaryDao.queryEq(type, val, null, "1");
		} else {
			dictionaries = dictionaryDao.query(type, val, null, "1");
		}
		return this.copyDicList(dictionaries);
	}

	private void copyProperties(Dictionary dictionary, DictionaryVo dictionaryVo) {
		dictionaryVo.setId(dictionary.getId().toString());
		dictionaryVo.setName(dictionary.getText());
		dictionaryVo.setType(dictionary.getType());
		dictionaryVo.setVal(dictionary.getVal());
		dictionaryVo.setLeaf(isLeaf(dictionary));
		dictionaryVo.setDescription(dictionary.getDescription());
	}

	public boolean isLeaf(Dictionary dictionary) {
		return dictionaryDao.findChildrenCount(dictionary.getVal()) == 0;
	}

	public Dictionary create(Long parentId, Dictionary dictionary) {
		if (parentId == null || parentId.longValue() == 0L) {
			parentId = 1L;
		}
		Dictionary parent = (Dictionary) dictionaryDao.reload(new Dictionary(parentId));
		dictionary.setType(parent.getVal());
		dictionary.setActive("1");
		dictionaryDao.save(dictionary);
		return dictionary;
	}

	@Override
	public void delete(Long id) {
		dictionaryDao.delete(new Dictionary(id));
	}

	public IDictionaryDao getDictionaryDao() {
		return dictionaryDao;
	}

	public Serializable save(Dictionary dictionary) {
		return dictionaryDao.save(dictionary);
	}

	public void delete(Dictionary dictionary) {
		dictionaryDao.delete(dictionary);
	}

	public void deleteByExample(Dictionary dictionary) {
		dictionaryDao.deleteByExample(dictionary);
	}

	public void update(Dictionary dictionary) {
		dictionaryDao.update(dictionary);
	}

	public void updateIgnoreNull(Dictionary dictionary) {
		dictionaryDao.updateIgnoreNull(dictionary);
	}

	public void updateByExample(Dictionary dictionary) {
		dictionaryDao.update("updateByExampleDictionary", dictionary);
	}

	public Dictionary load(Dictionary dictionary) {
		return (Dictionary) dictionaryDao.reload(dictionary);
	}

	public List<Dictionary> selectForList(Dictionary dictionary) {
		return (List<Dictionary>) dictionaryDao.selectForList("selectDictionary", dictionary);
	}

	@Override
	public Long findChildrenCount(String type) {
		return dictionaryDao.findChildrenCount(type);
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
	public Dictionary load(String type, String val) {
		try {
			return dictionaryDao.load(type, val);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public Dictionary loadByTypeText(String type, String text) {
		try {
			return dictionaryDao.loadByTypeText(type, text);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<Dictionary> query(String type, String val, String text, String active) {
		return dictionaryDao.query(type, val, text, active);
	}

	@Override
	public List<Dictionary> queryEq(String type, String val, String text, String active) {
		return dictionaryDao.queryEq(type, val, text, active);
	}

	@Autowired
	public void setIDictionaryDao(@Qualifier("dictionaryDao") IDictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}

}
