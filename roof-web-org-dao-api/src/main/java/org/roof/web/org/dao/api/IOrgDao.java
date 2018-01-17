package org.roof.web.org.dao.api;

import java.util.List;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;
import org.roof.web.org.entity.Organization;

public interface IOrgDao extends IDaoSupport {

	public Organization load(Long id);

	public abstract List<Organization> findOrgByParent(Long parentId);

	/**
	 * 逻辑删除
	 * 
	 * @param org
	 */
	public abstract void disable(Organization org);

	public abstract Long childrenCount(Long parentId);

	public abstract List<Long> selectOrgIds(Long parId);
	
	Page page(Page page, Organization organization);

}