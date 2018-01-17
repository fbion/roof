package org.roof.web.resource.dao.api;

import java.util.Collection;
import java.util.List;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.web.resource.entity.Resource;

public interface IResourceDao extends IDaoSupport {

	public abstract List<Resource> loadAll();

	public abstract List<Resource> findModuleByParent(Long parentId);

	public abstract List<Resource> findModuleByPath(String path);

	public abstract List<Resource> findModuleByPath(String[] path);

	public abstract Long childrenCount(Long id);

	public abstract Collection<Resource> selectByUser(Long userId);

	public abstract Collection<Resource> selectByRole(Long roleId);

}