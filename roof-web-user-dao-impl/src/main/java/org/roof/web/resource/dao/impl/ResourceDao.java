package org.roof.web.resource.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.web.resource.dao.api.IResourceDao;
import org.roof.web.resource.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ResourceDao extends AbstractDao implements IResourceDao {

	public List<Resource> loadAll() {
		@SuppressWarnings("unchecked")
		List<Resource> resources = (List<Resource>) daoSupport.selectForList("org.roof.web.resource.dao.loadAll");
		return resources;
	}

	public List<Resource> findModuleByParent(Long parentId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", parentId);
		if (parentId == null) {
			param.put("lvl", 0);
		}
		@SuppressWarnings("unchecked")
		List<Resource> resources = (List<Resource>) daoSupport
				.selectForList("org.roof.web.resource.dao.findModuleByParent", param);
		return resources;
	}

	@Deprecated
	public List<Resource> findModuleByPath(String path) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("path", path);
		@SuppressWarnings("unchecked")
		List<Resource> resources = (List<Resource>) daoSupport
				.selectForList("org.roof.web.resource.dao.findModuleByParent", param);
		return resources;
	}

	public List<Resource> findModuleByPath(String[] path) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pathArr", path);
		@SuppressWarnings("unchecked")
		List<Resource> resources = (List<Resource>) daoSupport
				.selectForList("org.roof.web.resource.dao.findPrivilegeByParent", param);
		return resources;
	}

	public Long childrenCount(Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", id);
		Long result = (Long) daoSupport.selectForObject("org.roof.web.resource.dao.childrenCount", param);
		return result;
	}

	@SuppressWarnings("unchecked")
	public Collection<Resource> selectByUser(Long userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		return (Collection<Resource>) daoSupport.selectForList("org.roof.web.resource.dao.selectByUser", param);
	}

	@SuppressWarnings("unchecked")
	public Collection<Resource> selectByRole(Long roleId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleId", roleId);
		return (Collection<Resource>) daoSupport.selectForList("org.roof.web.resource.dao.selectByRole", param);
	}

	public IDaoSupport getDaoSupport() {
		return daoSupport;
	}

	@Override
	@Autowired(required = true)
	public void setDaoSupport(@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

}
