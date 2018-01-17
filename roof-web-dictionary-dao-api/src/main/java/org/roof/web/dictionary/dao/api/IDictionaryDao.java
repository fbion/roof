package org.roof.web.dictionary.dao.api;

import java.util.List;

import org.omg.CORBA.portable.ApplicationException;
import org.roof.roof.dataaccess.api.DaoException;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.web.dictionary.entity.Dictionary;

public interface IDictionaryDao extends IDaoSupport {

	public abstract List<Dictionary> findByType(String type);

	public abstract Long findChildrenCount(String type);

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
	public abstract Dictionary load(String type, String val)
			throws DaoException;

	public abstract Dictionary loadByTypeText(String type, String text)
			throws DaoException;

	public abstract List<Dictionary> query(String type, String val,
			String text, String active);

	public abstract List<Dictionary> queryEq(String type, String val,
			String text, String active);
}