package org.roof.web.dictionary.service.api;

import java.io.Serializable;
import java.util.List;

import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.entity.DictionaryVo;

public interface IDictionaryService {

	public abstract List<Dictionary> findByType(String type);

	public abstract List<DictionaryVo> read(String type);

	public abstract List<DictionaryVo> readVal(String type, String val);

	public abstract boolean isLeaf(Dictionary dictionary);

	/**
	 * 创建一个资源
	 * 
	 * @param type
	 *            module 模块, privilege 资源(权限)
	 * @return
	 */
	public abstract Dictionary create(Long parentId, Dictionary dictionary);

	/**
	 * 删除一个组织
	 * 
	 * @param id
	 */
	public abstract void delete(Long id);

	/**
	 * 将对象保存，返回该条记录的操作数量，保存成功之后，将主键填充到参数对象中
	 */
	public abstract Serializable save(Dictionary dictionary);

	/**
	 * 按对象中的主键进行删除，如果是DRDS，还需要添加拆分键
	 */
	public abstract void delete(Dictionary dictionary);

	/**
	 * 按对象中的非空属性作为条件，进行删除
	 */
	public abstract void deleteByExample(Dictionary dictionary);

	/**
	 * 按对象中的主键进行所有属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void update(Dictionary dictionary);

	/**
	 * 按对象中的主键进行所有非空属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void updateIgnoreNull(Dictionary dictionary);

	/**
	 * 按对象中的非空属性作为条件，进行修改
	 */
	public abstract void updateByExample(Dictionary dictionary);

	/**
	 * 按对象中的主键进行数据加载，如果是DRDS，还需要添加拆分键
	 */
	public abstract Dictionary load(Dictionary dictionary);

	/**
	 * 按对象中的非空属性作为条件，进行查询
	 */
	public abstract List<Dictionary> selectForList(Dictionary dictionary);

	Long findChildrenCount(String type);

	Dictionary load(String type, String val);

	Dictionary loadByTypeText(String type, String text);

	List<Dictionary> query(String type, String val, String text, String active);

	List<Dictionary> queryEq(String type, String val, String text, String active);

}