package org.roof.web.user.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.roof.web.role.entity.BaseRole;
import org.roof.web.role.entity.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 加载用户并且加上默认的角色
 * 
 * @author liuxin
 *
 */
public class DefaultRolesUserDetailsService extends UserDetailsServiceImpl {
	private Long[] roleIds;
	private Collection<BaseRole> roles;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = super.loadUserByUsername(username);
		loadDefaultRoles();
		@SuppressWarnings("unchecked")
		Collection<BaseRole> authorities = (Collection<BaseRole>) userDetails.getAuthorities();
		for (BaseRole grantedAuthority : roles) {
			if (!exists(authorities, grantedAuthority.getId())) {
				authorities.add(grantedAuthority);
			}
		}
		return userDetails;
	}

	private boolean exists(Collection<BaseRole> authorities, Long roleId) {
		for (BaseRole authority : authorities) {
			if (authority.getId().longValue() == roleId.longValue()) {
				return true;
			}
		}
		return false;
	}

	private void loadDefaultRoles() {
		if (roles == null) {
			roles = new ArrayList<BaseRole>();
			for (Long roleId : roleIds) {
				roles.add(getRoleDao().load(Role.class, roleId));
			}
		}

	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

}
