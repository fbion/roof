package org.roof.web.log.service.api;

import java.util.List;
import java.io.Serializable;

import org.roof.roof.dataaccess.api.Page;
import org.roof.web.log.entity.LoginLog;
import org.roof.web.log.entity.LoginLogVo;

public interface ILoginLogService {

	/**
	 * 将对象保存，返回该条记录的操作数量，保存成功之后，将主键填充到参数对象中
	 */
	public abstract Serializable save(LoginLog loginLog);

	/**
	 * 按对象中的主键进行删除，如果是DRDS，还需要添加拆分键
	 */
	public abstract void delete(LoginLog loginLog);
	
	/**
	 * 按对象中的非空属性作为条件，进行删除
	 */
	public abstract void deleteByExample(LoginLog loginLog);

	/**
	 * 按对象中的主键进行所有属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void update(LoginLog loginLog);
	
	/**
	 * 按对象中的主键进行所有非空属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void updateIgnoreNull(LoginLog loginLog);
	
	/**
	 * 按对象中的非空属性作为条件，进行修改
	 */
	public abstract void updateByExample(LoginLog loginLog);

	/**
	 * 按对象中的主键进行数据加载，如果是DRDS，还需要添加拆分键
	 */
	public abstract LoginLogVo load(LoginLog loginLog);
	
	/**
	 * 按对象中的非空属性作为条件，进行查询
	 */
	public abstract List<LoginLogVo> selectForList(LoginLog loginLog);
	
	/**
	 * 按对象中的非空属性作为条件，进行分页查询
	 */
	public abstract Page page(Page page, LoginLog loginLog);

}