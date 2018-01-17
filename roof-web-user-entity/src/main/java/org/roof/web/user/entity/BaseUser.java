package org.roof.web.user.entity;

import java.beans.Transient;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Id;

import org.roof.web.role.entity.BaseRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户
 * 
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 RoofUser.java 2012-7-5
 */
public class BaseUser implements UserDetails {
	private static final long serialVersionUID = 4083274777376399633L;

	protected Long id;

	protected String username;// 用户名（登录工号）
	protected String password;// 密码
	protected boolean accountNonExpired = true; // 是否过期
	protected boolean accountNonLocked = true; // 是否锁定
	protected boolean credentialsNonExpired = true; // 密码是否被锁定
	protected boolean enabled = true; // 是否可用

	protected Set<BaseRole> roles;

	protected String dtype = this.getClass().getSimpleName();

	public Long getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}

	@Transient
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	// public boolean getAccountNonExpired() {
	// return accountNonExpired;
	// }
	//
	// public boolean getAccountNonLocked() {
	// return accountNonLocked;
	// }
	//
	// public boolean getCredentialsNonExpired() {
	// return credentialsNonExpired;
	// }
	//
	// public boolean getEnabled() {
	// return enabled;
	// }

	public Set<BaseRole> getRoles() {
		return roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setRoles(Set<BaseRole> roles) {
		this.roles = roles;
	}

	public void setId(Long id) {
		this.id = id;
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
		BaseUser other = (BaseUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
