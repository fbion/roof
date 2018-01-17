package org.roof.web.role.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.roof.dataaccess.PageQuery;
import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.IPageQuery;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageQueryFactory;
import org.roof.web.role.dao.api.IRoleDao;
import org.roof.web.role.entity.BaseRole;
import org.roof.web.role.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RoleDao extends AbstractDao implements IRoleDao {
	private PageQueryFactory<PageQuery> pageQueryFactory;

	public Page page(Page page, Role params) {
		IPageQuery pageQuery = pageQueryFactory.getPageQuery(page, "org.roof.web.role.dao.list",
				"org.roof.web.role.dao.list_count");
		return pageQuery.select(params);
	}

	public List<Role> listVo(Role params) {
		@SuppressWarnings("unchecked")
		List<Role> rs = (List<Role>) this.selectForList("org.roof.web.role.dao.listVo", params);
		return rs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<BaseRole> selectByResource(Long resourceId) {
		return (Collection<BaseRole>) daoSupport.selectForList("org.roof.web.role.dao.selectByResource", resourceId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Role> selectByUser(Long userId) {
		List<Role> baseRoles = (List<Role>) daoSupport.selectForList("org.roof.web.role.dao.selectByUser", userId);
		Set<Role> result = new HashSet<Role>();
		result.addAll(baseRoles);
		return result;
	}

	@Override
	public int clearResources(Long roleId) {
		return daoSupport.delete("org.roof.web.role.dao.clearResources", roleId);
	}

	@Override
	public int addResources(Long roleId, Long resourceId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("resourceId", resourceId);
		long l = (long) daoSupport.selectForObject("org.roof.web.role.dao.existRoleResource", params);
		if (l > 0) {
			return 0;
		}
		return daoSupport.update("org.roof.web.role.dao.addResources", params);
	}

	@Override
	public int removeResouce(Long roleId, Long resourceId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("resourceId", resourceId);
		return daoSupport.delete("org.roof.web.role.dao.removeResouce", params);
	}

	@Override
	@Autowired(required = true)
	public void setDaoSupport(@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

	@Autowired(required = true)
	public void setPageQueryFactory(@Qualifier("pageQueryFactory") PageQueryFactory<PageQuery> pageQueryFactory) {
		this.pageQueryFactory = pageQueryFactory;
	}

}
