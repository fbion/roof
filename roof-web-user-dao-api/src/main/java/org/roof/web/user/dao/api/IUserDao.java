package org.roof.web.user.dao.api;

import java.util.List;

import org.roof.roof.dataaccess.api.DaoException;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;
import org.roof.web.user.entity.User;

public interface IUserDao extends IDaoSupport {

	public abstract Long readUserCount(User user);

	public abstract Page page(Page page, User user);

	public abstract List<User> list(User user);

	public User loadUserByUsername(String username) throws DaoException;

	long selectUserRoleCount(Long userId);

	int addUserRole(Long userId, Long roleId);

	int clearUserRole(Long userId);

}