package org.roof.web.resource.service.api;

import java.util.List;

import org.roof.web.resource.entity.Privilege;
import org.roof.web.resource.entity.Resource;
import org.roof.web.resource.entity.ResourceVo;

public interface IResourceService {

	public abstract List<Resource> findModuleByPath(String path);

	public abstract List<Resource> findModuleByPath(String[] pathArr);

	public abstract List<ResourceVo> read(Long parentId, String basePath);

	public abstract List<ResourceVo> readByRole(Long parentId, Long roleId, String basePath);

	public abstract void copyProperties(Resource resource, ResourceVo resourceVo, String basePath);

	/**
	 * 创建一个资源
	 * 
	 * @param type
	 *            module 模块, privilege 资源(权限)
	 * @return
	 */
	public abstract Resource create(Long parentId, String type);

	/**
	 * 更新一个资源
	 * 
	 * @param resource
	 * @return
	 */
	public abstract Resource update(Resource resource);

	/**
	 * 删除一个资源
	 * 
	 * @param id
	 */
	public abstract void delete(Long id);

	public abstract Privilege load(Long id);

	public abstract void updateIgnoreNull(Privilege privilege);

	public abstract List<Resource> findModuleByParent(Long parentId);

}