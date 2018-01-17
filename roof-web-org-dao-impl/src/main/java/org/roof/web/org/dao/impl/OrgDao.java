package org.roof.web.org.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roof.dataaccess.PageQuery;
import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.IPageQuery;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageQueryFactory;
import org.roof.web.org.dao.api.IOrgDao;
import org.roof.web.org.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrgDao extends AbstractDao implements IOrgDao {
	private PageQueryFactory<PageQuery> pageQueryFactory;

	@Override
	public Organization load(Long id) {
		return (Organization) daoSupport.selectForObject("org.roof.web.org.dao.loadOrganization", id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.web.org.dao.impl.IOrgDao#findOrgByParent(java.lang.Long)
	 */
	@Override
	public List<Organization> findOrgByParent(Long parentId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", parentId);
		if (parentId == null) {
			param.put("lvl", 0);
		}
		param.put("usable", true);
		@SuppressWarnings("unchecked")
		List<Organization> orgs = (List<Organization>) daoSupport.selectForList("org.roof.web.org.dao.findOrgByParent",
				param);
		return orgs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.web.org.dao.impl.IOrgDao#disable(org.roof.web.org.entity.
	 * Organization)
	 */
	@Override
	public void disable(Organization org) {
		org.setUsable(false);
		daoSupport.update(org);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.roof.web.org.dao.impl.IOrgDao#childrenCount(java.lang.Long)
	 */
	@Override
	public Long childrenCount(Long parentId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", parentId);

		Long result = (Long) daoSupport.selectForObject("org.roof.web.org.dao.childrenCount", param);
		return result;
	}

	public List<Long> selectOrgIds(Long parentId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", parentId);
		return (List<Long>) daoSupport.selectForList("org.roof.web.org.dao.selectOrgIds", param);
	}

	public Page page(Page page, Organization organization) {
		IPageQuery pageQuery = pageQueryFactory.getPageQuery(page, "selectOrganizationPage", "selectOrganizationCount");
		return pageQuery.select(organization);
	}

	@Autowired
	public void setDaoSupport(@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

	@Autowired
	public void setPageQueryFactory(@Qualifier("pageQueryFactory") PageQueryFactory<PageQuery> pageQueryFactory) {
		this.pageQueryFactory = pageQueryFactory;
	}

	public IDaoSupport getDaoSupport() {
		return daoSupport;
	}

}
