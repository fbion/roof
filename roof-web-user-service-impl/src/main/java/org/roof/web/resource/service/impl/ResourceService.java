package org.roof.web.resource.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.web.resource.dao.api.IResourceDao;
import org.roof.web.resource.entity.Module;
import org.roof.web.resource.entity.Privilege;
import org.roof.web.resource.entity.Resource;
import org.roof.web.resource.entity.ResourceVo;
import org.roof.web.resource.service.api.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ResourceService implements IResourceService {
	private static final String RESOURCE_TYPE_ICON_RESOURCE = "/roof-web/web/resources/images/R.png";
	private static final String RESOURCE_TYPE_ICON_MODULE = "/roof-web/web/resources/images/M.png";
	private static final String RESOURCE_TYPE_ICON_QUERY = "/roof-web/web/resources/images/Q.png";
	private static final String RESOURCE_TYPE_ICON_QUERYFILTER = "/roof-web/web/resources/images/F.png";
	private static final String RESOURCE_TYPE_ICON_GROUP = "/roof-web/web/resources/images/G.png";

	public static final String RESOURCE_TYPE_MODULE = "module";
	public static final String RESOURCE_TYPE_PRIVILEGE = "privilege";
	public static final String RESOURCE_TYPE_QUERY = "query";
	public static final String RESOURCE_TYPE_QUERYFILTER = "queryfilter";
	private IResourceDao resourceDao;

	@Override
	@Deprecated
	public List<Resource> findModuleByPath(String path) {
		return resourceDao.findModuleByPath(path);
	}

	@Override
	public List<Resource> findModuleByPath(String[] pathArr) {
		return resourceDao.findModuleByPath(pathArr);
	}

	@Override
	public List<ResourceVo> read(Long parentId, String basePath) {
		List<Resource> resources = resourceDao.findModuleByParent(parentId);
		List<ResourceVo> result = new ArrayList<ResourceVo>();
		for (Resource resource : resources) {
			ResourceVo resourceVo = new ResourceVo();
			copyProperties(resource, resourceVo, basePath);
			result.add(resourceVo);
		}
		return result;
	}

	@Override
	public List<ResourceVo> readByRole(Long parentId, Long roleId, String basePath) {
		List<ResourceVo> resourceVos = read(parentId, basePath);
		// BaseRole baseRole = resourceDao.load(Role.class, roleId);
		Collection<Resource> resources = resourceDao.selectByRole(roleId);
		for (Resource resource : resources) {
			for (ResourceVo resourceVo : resourceVos) {
				if (StringUtils.equals(resourceVo.getId(), resource.getId().toString())) {
					resourceVo.setChecked(true);
				}
			}
		}
		return resourceVos;
	}

	@Override
	public void copyProperties(Resource resource, ResourceVo resourceVo, String basePath) {
		if (resource instanceof Module) {
			resourceVo.setType(RESOURCE_TYPE_MODULE);
			resourceVo.setIcon(basePath + RESOURCE_TYPE_ICON_MODULE);
			Module module = (Module) resource;
			resourceVo.setLeaf(module.getLeaf());
		}
		if (StringUtils.equals(resource.getDtype(), "Privilege")) {
			resourceVo.setType(RESOURCE_TYPE_PRIVILEGE);
			resourceVo.setIcon(basePath + RESOURCE_TYPE_ICON_RESOURCE);
			Privilege privilege = (Privilege) resource;
			resourceVo.setLeaf(privilege.getLeaf());
		}
		/*
		 * if (resource instanceof QueryResource) {
		 * resourceVo.setType(RESOURCE_TYPE_QUERY); resourceVo.setIcon(basePath
		 * + RESOURCE_TYPE_ICON_QUERY); QueryResource queryResource =
		 * (QueryResource) resource;
		 * resourceVo.setLeaf(queryResource.getLeaf()); }
		 * 
		 * if (resource instanceof QueryFilterResource) {
		 * resourceVo.setType(RESOURCE_TYPE_QUERYFILTER);
		 * resourceVo.setIcon(basePath + RESOURCE_TYPE_ICON_QUERYFILTER);
		 * QueryFilterResource queryFilterResource = (QueryFilterResource)
		 * resource; resourceVo.setLeaf(queryFilterResource.getLeaf()); }
		 */
		resourceVo.setId(resource.getId().toString());
		resourceVo.setName(resource.getName());
		resourceVo.setTitle(resource.getPattern());
	}

	/**
	 * 创建一个资源
	 * 
	 * @param type
	 *            module 模块, privilege 资源(权限)
	 * @return
	 */
	@Override
	public Resource create(Long parentId, String type) {
		Privilege privilege = new Privilege();
		Privilege parent = resourceDao.load(Privilege.class, parentId);
		if (StringUtils.equals(RESOURCE_TYPE_MODULE, type)) {
			privilege.setDtype("Module");
		}
		if (StringUtils.equals(RESOURCE_TYPE_PRIVILEGE, type)) {
			privilege.setDtype("Privilege");
		}
		if (StringUtils.equals(RESOURCE_TYPE_QUERY, type)) {
			privilege.setDtype("QueryResource");
		}
		if (StringUtils.equals(RESOURCE_TYPE_QUERYFILTER, type)) {
			privilege.setDtype("QueryFilterResource");
		}
		if (parent.getLeaf()) {
			parent.setLeaf(false);
			resourceDao.update(parent);
		}
		privilege.setName("未命名");
		privilege.setLvl(parent.getLvl() + 1);
		privilege.setParent(parent);
		privilege.setLeaf(true);
		resourceDao.save(privilege);
		return privilege;
	}

	/**
	 * 更新一个资源
	 * 
	 * @param resource
	 * @return
	 */
	@Override
	public Resource update(Resource resource) {
		return resource;
	}

	/**
	 * 删除一个资源
	 * 
	 * @param id
	 */
	@Override
	public void delete(Long id) {
		Module resource = resourceDao.load(Privilege.class, id);
		Module parent = (Module) resource.getParent();
		if (parent != null && parent.getId() != null) {
			Long count = resourceDao.childrenCount(parent.getId());
			if (count == 1) {
				parent.setLeaf(true);
				resourceDao.updateIgnoreNull(parent, parent.getId());
			}
		}
		resourceDao.delete(resource);
	}

	@Override
	public Privilege load(Long id) {
		return (Privilege) resourceDao.load(Privilege.class, id);
	}

	@Override
	public void updateIgnoreNull(Privilege privilege) {
		resourceDao.updateIgnoreNull(privilege);
	}

	@Override
	public List<Resource> findModuleByParent(Long parentId) {
		return resourceDao.findModuleByParent(parentId);
	}

	public IResourceDao getResourceDao() {
		return resourceDao;
	}

	@Autowired(required = true)
	public void setResourceDao(@Qualifier("resourceDao") IResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	public IDaoSupport getDaoSupport() {
		return resourceDao;
	}

}
