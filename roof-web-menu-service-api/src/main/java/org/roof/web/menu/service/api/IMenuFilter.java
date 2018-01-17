package org.roof.web.menu.service.api;

import java.util.List;

import org.roof.web.menu.entity.Menu;
import org.roof.web.user.entity.User;
import org.springframework.security.core.Authentication;

public interface IMenuFilter {

	public abstract Menu doFilter(Long id, Authentication authentication);

	public abstract Menu doFilter(Menu menu, Authentication authentication);

	public abstract List<Menu> doFilter(List<Menu> menus,
			Authentication authentication);

	public abstract List<Menu> filterResource(List<Menu> menus, User user);

}