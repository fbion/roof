package org.roof.web.org.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.roof.roof.dataaccess.api.Page;
import org.roof.web.org.dao.api.IOrgDao;
import org.roof.web.org.entity.OrgVo;
import org.roof.web.org.entity.Organization;
import org.roof.web.org.entity.OrganizationVo;
import org.roof.web.org.service.api.IOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrgService implements IOrgService {
	private IOrgDao orgDao;

	@Override
	public Organization load(Long id) {
		return orgDao.load(id);
	}

	/**
	 * 读取自己
	 * 
	 * @param parentId
	 * @return
	 */
	@Override
	public List<OrgVo> readMyNode(Long parentId) {
		Organization org = this.load(parentId);
		List<OrgVo> result = new ArrayList<OrgVo>();
		OrgVo orgVo = new OrgVo();
		copyProperties(org, orgVo);
		result.add(orgVo);
		return result;
	}

	@Override
	public List<OrgVo> read(Long parentId) {
		List<Organization> orgs = orgDao.findOrgByParent(parentId);
		return this.read(orgs);
	}

	@Override
	public List<OrgVo> read(List<Organization> orgs) {
		List<OrgVo> result = new ArrayList<OrgVo>();
		for (Organization org : orgs) {
			OrgVo orgVo = new OrgVo();
			copyProperties(org, orgVo);
			result.add(orgVo);
		}
		return result;
	}

	private void copyProperties(Organization org, OrgVo orgVo) {
		orgVo.setId(org.getId().toString());
		orgVo.setName(org.getName());
		orgVo.setLeaf(org.getLeaf());
		orgVo.setLvl(org.getLvl());
	}

	/**
	 * 创建一个资源
	 * 
	 * @param type
	 *            module 模块, privilege 资源(权限)
	 * @return
	 */
	@Override
	public Organization create(Long parentId, Organization org) {
		if (parentId == null || parentId.longValue() == 0L) {
			parentId = 1L;
		}
		Organization parent = orgDao.load(parentId);
		if (parent.getLeaf() != null && parent.getLeaf()) {
			parent.setLeaf(false);
			orgDao.update(parent);
		}
		org.setParent_id(parentId);
		org.setLvl(parent.getLvl() + 1);
		org.setLeaf(true);
		org.setUsable(true);
		orgDao.save(org);
		return org;
	}

	/**
	 * 删除一个组织
	 * 
	 * @param id
	 */
	@Override
	public void delete(Long id) {
		Organization org = orgDao.load(Organization.class, id);
		Organization parent = orgDao.load(org.getParent_id());
		Long count = orgDao.childrenCount(parent.getId());
		if (count == 1) {
			parent.setLeaf(true);
			orgDao.update(parent);
		}
		org.setId(id);
		// daoSupport.delete(org);
		orgDao.disable(org);
	}

	public Serializable save(Organization organization) {
		return orgDao.save(organization);
	}

	public void delete(Organization organization) {
		orgDao.delete(organization);
	}

	public void deleteByExample(Organization organization) {
		orgDao.deleteByExample(organization);
	}

	public void update(Organization organization) {
		orgDao.update(organization);
	}

	public void updateIgnoreNull(Organization organization) {
		orgDao.updateIgnoreNull(organization);
	}

	public void updateByExample(Organization organization) {
		orgDao.update("updateByExampleOrganization", organization);
	}

	public OrganizationVo load(Organization organization) {
		return (OrganizationVo) orgDao.reload(organization);
	}

	public List<OrganizationVo> selectForList(Organization organization) {
		return (List<OrganizationVo>) orgDao.selectForList("selectOrganization", organization);
	}

	public Page page(Page page, Organization organization) {
		return orgDao.page(page, organization);
	}

	@Autowired(required = true)
	public void setOrgDao(@Qualifier("orgDao") IOrgDao orgDao) {
		this.orgDao = orgDao;
	}

	public IOrgDao getOrgDao() {
		return orgDao;
	}

}
