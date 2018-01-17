package org.roof.web.role.dao.api;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;
import org.roof.web.role.entity.BaseRole;
import org.roof.web.role.entity.Role;

public interface IRoleDao extends IDaoSupport {

	public abstract Page page(Page page, Role params);

	public abstract List<Role> listVo(Role params);

	public Collection<BaseRole> selectByResource(Long resourceId);

	Set<Role> selectByUser(Long userId);

	int clearResources(Long roleId);

	int removeResouce(Long roleId, Long resourceId);

	int addResources(Long roleId, Long resourceId);

}