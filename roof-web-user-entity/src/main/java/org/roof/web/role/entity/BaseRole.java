package org.roof.web.role.entity;

import java.util.Set;

import org.roof.web.resource.entity.Resource;
import org.springframework.security.core.GrantedAuthority;

/**
 * 角色
 * 
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 RoofRole.java 2012-7-5
 */
public class BaseRole implements GrantedAuthority {

	private static final long serialVersionUID = 7539222051677360527L;
	protected Long id;
	protected String name;
	protected Set<Resource> resources; // 可以访问的资源
	protected String dtype = this.getClass().getSimpleName();

	public BaseRole() {
		super();
	}

	public BaseRole(Long id) {
		super();
		this.id = id;
	}

	public BaseRole(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	public String getAuthority() {
		if (id == null) {
			return null;
		}
		return "ROLE_" + id.toString();
	}

	public void setAuthority(String authority) {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseRole other = (BaseRole) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
