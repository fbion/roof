package org.roof.web.resource.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Id;

import org.roof.web.role.entity.BaseRole;

/**
 * 资源
 * 
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 Resource.java 2011-11-9
 */
public class Resource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7349020716543923233L;
	protected Long id;
	protected String name;
	protected String pattern;
	protected String description;
	protected String dtype;
	protected Set<BaseRole> baseRole;

	@Id
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPattern() {
		return pattern;
	}

	public Set<BaseRole> getBaseRole() {
		return baseRole;
	}

	public String getDescription() {
		return description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBaseRole(Set<BaseRole> baseRole) {
		this.baseRole = baseRole;
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
		Resource other = (Resource) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}