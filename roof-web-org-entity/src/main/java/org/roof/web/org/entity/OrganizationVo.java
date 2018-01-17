package org.roof.web.org.entity;

import java.util.List;

/**
 * @author 模版生成 <br/>
 *         表名： s_organization <br/>
 *         描述：s_organization <br/>
 */
public class OrganizationVo extends Organization {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8684003134216183278L;
	private List<OrganizationVo> organizationList;

	public OrganizationVo() {
		super();
	}

	public OrganizationVo(Long id) {
		super();
		this.id = id;
	}

	public List<OrganizationVo> getOrganizationList() {
		return organizationList;
	}

	public void setOrganizationList(List<OrganizationVo> organizationList) {
		this.organizationList = organizationList;
	}

}
