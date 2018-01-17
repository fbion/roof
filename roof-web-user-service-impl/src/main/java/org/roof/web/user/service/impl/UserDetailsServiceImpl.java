package org.roof.web.user.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.roof.roof.dataaccess.api.DaoException;
import org.roof.web.role.dao.api.IRoleDao;
import org.roof.web.role.entity.BaseRole;
import org.roof.web.user.dao.api.IUserDao;
import org.roof.web.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.security.provisioning.UserDetailsManager;

/**
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 UserDetailsServiceImpl.java 2012-7-4
 */
public class UserDetailsServiceImpl implements UserDetailsService, UserDetailsManager, GroupManager {

	private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);

	private IUserDao userDao;
	private IRoleDao roleDao;
	private List<String> userClasses;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		String userClassName = null;
		try {
			user = userDao.loadUserByUsername(username);
			if (user == null) {
				if (logger.isInfoEnabled()) {
					logger.info("loadUserByUsername(String) - 没有找到用户名 [" + username + "]");
				}
				throw new UsernameNotFoundException("用户名[" + username + "]未找到");
			}
			if (userClasses == null) {
				addRoles(user);
				return user;
			}

			for (String userClass : userClasses) {
				if (clear(StringUtils.substringAfterLast(userClass, ".")).equals(user.getDtype())) {
					userClassName = userClass;
				}
			}
			if (userClassName == null) {
				addRoles(user);
				return user;
			}
			user = (User) userDao.load(Class.forName(clear(userClassName)), user.getId());
			if (logger.isInfoEnabled()) {
				logger.info("加载登录用户类型[" + user.getClass().getName() + "]");
			}
		} catch (DaoException e) {
			throw new UsernameNotFoundException("用户名[" + username + "]存在多个可用账号");
		} catch (ClassNotFoundException e) {
			throw new UsernameNotFoundException("用户类型[" + clear(userClassName) + "]未找到");
		}
		addRoles(user);
		return user;
	}

	private String clear(String s) {
		String result = StringUtils.replaceEachRepeatedly(s, new String[] { " ", "\n", "\t" },
				new String[] { "", "", "" });
		return result;
	}

	private void addRoles(User user) {
		if (user instanceof User) {
			User u = (User) user;
			Set<?> baseRoles = roleDao.selectByUser(u.getId());
			u.setRoles((Set<BaseRole>) baseRoles);
		}
	}

	@Override
	public List<String> findAllGroups() {
		return null;
	}

	@Override
	public List<String> findUsersInGroup(String groupName) {
		return null;
	}

	@Override
	public void createGroup(String groupName, List<GrantedAuthority> authorities) {

	}

	@Override
	public void deleteGroup(String groupName) {

	}

	@Override
	public void renameGroup(String oldName, String newName) {
	}

	@Override
	public void addUserToGroup(String username, String group) {

	}

	@Override
	public void removeUserFromGroup(String username, String groupName) {

	}

	@Override
	public List<GrantedAuthority> findGroupAuthorities(String groupName) {
		return null;
	}

	@Override
	public void addGroupAuthority(String groupName, GrantedAuthority authority) {

	}

	@Override
	public void removeGroupAuthority(String groupName, GrantedAuthority authority) {

	}

	@Override
	public void createUser(UserDetails user) {

	}

	@Override
	public void updateUser(UserDetails user) {

	}

	@Override
	public void deleteUser(String username) {

	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {

	}

	@Override
	public boolean userExists(String username) {
		return false;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public List<String> getUserClasses() {
		return userClasses;
	}

	public void setUserClasses(List<String> userClasses) {
		this.userClasses = userClasses;
	}

}
