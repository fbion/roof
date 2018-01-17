package org.roof.web.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.DaoException;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.IPageQuery;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageQueryFactory;
import org.roof.web.user.dao.api.IUserDao;
import org.roof.web.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserDao extends AbstractDao implements IUserDao {

	private PageQueryFactory pageQueryFactory;

	public Long readUserCount(User user) {
		Long l = (Long) this.selectForObject("org.roof.web.user.dao.selectUserCount", user);
		return l;
	}

	public Page page(Page page, User user) {
		IPageQuery pageQuery = pageQueryFactory.getPageQuery(page, "org.roof.web.user.dao.selectUserPage",
				"org.roof.web.user.dao.selectUserCount");
		return pageQuery.select(user);
	}

	public List<User> list(User user) {
		@SuppressWarnings("unchecked")
		List<User> staffs = (List<User>) this.selectForList("org.roof.web.user.dao.selectUser", user);
		return staffs;
	}

	public User loadUserByUsername(String username) throws DaoException {
		User user = new User();
		user.setDtype(null);
		user.setUsername(username);
		return (User) this.selectForObject("org.roof.web.user.dao.selectUser", user);
	}

	@Override
	public long selectUserRoleCount(Long userId) {
		return (Long) this.selectForObject("org.roof.web.user.dao.selectUserRoleCount", userId);
	}

	@Override
	public int addUserRole(Long userId, Long roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("roleId", roleId);
		return this.update("org.roof.web.user.dao.addUserRole", params);
	}

	@Override
	public int clearUserRole(Long userId) {
		return this.update("org.roof.web.user.dao.clearUserRole", userId);
	}

	@Override
	@Autowired(required = true)
	public void setDaoSupport(@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;

	}

	@Autowired(required = true)
	public void setPageQueryFactory(@Qualifier("pageQueryFactory") PageQueryFactory pageQueryFactory) {
		this.pageQueryFactory = pageQueryFactory;
	}
}
