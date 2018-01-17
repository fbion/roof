package org.roof.web.role.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.roof.roof.dataaccess.api.Page;
import org.roof.web.resource.dao.api.IResourceDao;
import org.roof.web.resource.entity.Privilege;
import org.roof.web.resource.entity.Resource;
import org.roof.web.role.dao.api.IRoleDao;
import org.roof.web.role.entity.BaseRole;
import org.roof.web.role.entity.Role;
import org.roof.web.role.service.api.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService implements IRoleService {

	private IRoleDao roleDao;
	private IResourceDao resourceDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Role role, String allSrc, String selSrc) {
		Role old = (Role) roleDao.load(Role.class, role.getId());
		old.setName(role.getName());
		old.setDescription(role.getDescription());
		changeSrc(old, allSrc, selSrc);
		roleDao.update(old);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void changeSrc(BaseRole baseRole, String allsrc, String selsrc) {
		String[] allsrcIds = StringUtils.split(allsrc, ",");
		String[] selsrcIds = StringUtils.split(selsrc, ",");
		Set<Resource> resourcesSet = new HashSet<Resource>();
		Collection<Resource> resources = resourceDao.selectByRole(baseRole.getId());
		resourcesSet.addAll(resources);

		Iterator<Resource> iterator = resourcesSet.iterator();
		while (iterator.hasNext()) {
			Resource resource = (Resource) iterator.next();
			for (String allsrcId : allsrcIds) {
				if (StringUtils.equals(allsrcId, resource.getId().toString())) {
					iterator.remove();
					roleDao.removeResouce(baseRole.getId(), resource.getId());
				}
			}
		}

		for (String selsrcId : selsrcIds) {
			Resource resource = roleDao.load(Privilege.class, NumberUtils.createLong(selsrcId));
			resources.add(resource);
			roleDao.addResources(baseRole.getId(), resource.getId());
		}
		baseRole.setResources(resourcesSet);
	}

	public Page page(Page page, Role role) {
		return roleDao.page(page, role);
	}

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	@Override
	public Serializable save(Role role) {
		return roleDao.save(role);
	}

	@Override
	public Role load(Long id) {
		return roleDao.load(Role.class, id);
	}

	@Override
	public void delete(Role role) {
		roleDao.delete(role);
	}

	@Override
	public List<Role> loadAll() {
		return roleDao.loadAll(Role.class);
	}

	@Autowired(required = true)
	public void setRoleDao(@Qualifier("roleDao") IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Autowired(required = true)
	public void setResourceDao(@Qualifier("resourceDao") IResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

}
