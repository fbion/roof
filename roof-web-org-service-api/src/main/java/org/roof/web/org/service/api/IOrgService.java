package org.roof.web.org.service.api;

import java.io.Serializable;
import java.util.List;

import org.roof.roof.dataaccess.api.Page;
import org.roof.web.org.entity.OrgVo;
import org.roof.web.org.entity.Organization;
import org.roof.web.org.entity.OrganizationVo;

public interface IOrgService {

	public abstract Organization load(Long id);

	/**
	 * 读取自己
	 * 
	 * @param parentId
	 * @return
	 */
	public abstract List<OrgVo> readMyNode(Long parentId);

	public abstract List<OrgVo> read(Long parentId);

	public abstract List<OrgVo> read(List<Organization> orgs);

	/**
	 * 创建一个资源
	 * 
	 * @param type
	 *            module 模块, privilege 资源(权限)
	 * @return
	 */
	public abstract Organization create(Long parentId, Organization org);

	/**
	 * 删除一个组织
	 * 
	 * @param id
	 */
	public abstract void delete(Long id);

	/**
	 * 将对象保存，返回该条记录的操作数量，保存成功之后，将主键填充到参数对象中
	 */
	public abstract Serializable save(Organization organization);

	/**
	 * 按对象中的主键进行删除，如果是DRDS，还需要添加拆分键
	 */
	public abstract void delete(Organization organization);

	/**
	 * 按对象中的非空属性作为条件，进行删除
	 */
	public abstract void deleteByExample(Organization organization);

	/**
	 * 按对象中的主键进行所有属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void update(Organization organization);

	/**
	 * 按对象中的主键进行所有非空属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void updateIgnoreNull(Organization organization);

	/**
	 * 按对象中的非空属性作为条件，进行修改
	 */
	public abstract void updateByExample(Organization organization);

	/**
	 * 按对象中的主键进行数据加载，如果是DRDS，还需要添加拆分键
	 */
	public abstract OrganizationVo load(Organization organization);

	/**
	 * 按对象中的非空属性作为条件，进行查询
	 */
	public abstract List<OrganizationVo> selectForList(Organization organization);

	/**
	 * 按对象中的非空属性作为条件，进行分页查询
	 */
	public abstract Page page(Page page, Organization organization);

}