package org.roof.web.user.service.api;

import java.util.List;

import org.roof.roof.dataaccess.api.DaoException;
import org.roof.roof.dataaccess.api.Page;
import org.roof.web.user.entity.User;

public interface IUserService {

	public User load(Long id);

	public abstract Boolean sameUsername(Long id, String username);

	public abstract boolean isAdmin(User user);

	public abstract User login(User paramObj) throws DaoException;

	public abstract boolean isLoginError(String user_no);

	public abstract int findErrorCount(String user_no);

	public abstract List<User> list(User params);

	void save(User user);

	void update(User user);

	void delete(User user);

	public Page page(Page page, User user);

	public void updateIgnoreNull(User user);

}