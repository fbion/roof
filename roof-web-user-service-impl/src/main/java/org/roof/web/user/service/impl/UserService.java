package org.roof.web.user.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.roof.commons.PropertiesUtil;
import org.roof.commons.SysConstants;
import org.roof.roof.dataaccess.api.DaoException;
import org.roof.roof.dataaccess.api.Page;
import org.roof.web.org.dao.api.IOrgDao;
import org.roof.web.role.dao.api.IRoleDao;
import org.roof.web.role.entity.BaseRole;
import org.roof.web.user.dao.api.IUserDao;
import org.roof.web.user.entity.User;
import org.roof.web.user.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
	private IUserDao userDao;
	private IRoleDao roleDao;
	private IOrgDao orgDao;

	@Override
	public User load(Long id) {
		User user = userDao.load(User.class, id);
		Set<?> baseRoles = roleDao.selectByUser(id);
		user.setRoles((Set<BaseRole>) baseRoles);
		if (user.getOrg() != null && user.getOrg().getId() != null) {
			user.setOrg(orgDao.load(user.getOrg().getId()));
		}
		return user;
	}

	@Override
	public void delete(User user) {
		userDao.clearUserRole(user.getId());
		userDao.delete(user);
	}

	@Override
	public void save(User user) {
		userDao.save(user);
		addRoles(user);
	}

	@Override
	public void update(User user) {
		userDao.clearUserRole(user.getId());
		addRoles(user);
		userDao.updateIgnoreNull(user);
	}

	private void addRoles(User user) {
		if (user.getRoles() != null) {
			for (BaseRole role : user.getRoles()) {
				if (role != null && role.getId() != null) {
					userDao.addUserRole(user.getId(), role.getId());
				}
			}
		}
	}

	@Override
	public Boolean sameUsername(Long id, String username) {
		User user = new User();
		user.setUsername(username);
		Long l = userDao.readUserCount(user);
		if (l == 0) {
			return false;
		}
		if (id != null && id != 0L) {
			user = userDao.load(User.class, id);
			if (StringUtils.equals(username, user.getUsername())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isAdmin(User user) {
		boolean flg = false;
		Set<BaseRole> roles = user.getRoles();
		for (BaseRole baseRole : roles) {
			if (Long.valueOf(PropertiesUtil.getPorpertyString("core.superadmin")).equals(baseRole.getId())) {
				flg = true;
				break;
			}
		}
		return flg;
	}

	@Override
	public User login(User paramObj) throws DaoException {
		if (paramObj == null) {
			return null;
		}
		User result = (User) userDao.selectForObject("org.roof.web.user.dao.selectUser", paramObj);
		return result;
	}

	@Override
	public boolean isLoginError(String user_no) {
		return (this.findErrorCount(user_no) >= SysConstants.getAllowableErrorCount());
	}

	@Override
	public int findErrorCount(String user_no) {
		return 0;
	}

	@Override
	public List<User> list(User params) {
		return userDao.list(params);
	}

	@Override
	public Page page(Page page, User user) {
		return userDao.page(page, user);
	}

	@Override
	public void updateIgnoreNull(User user) {
		userDao.updateIgnoreNull(user);
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	@Autowired(required = true)
	public void setUserDao(@Qualifier("userDao") IUserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired(required = true)
	public void setRoleDao(@Qualifier("roleDao") IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Autowired(required = true)
	public void setOrgDao(@Qualifier("orgDao") IOrgDao orgDao) {
		this.orgDao = orgDao;
	}

}
