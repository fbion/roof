package org.roof.web.menu.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.roof.web.menu.dao.api.IMenuDao;
import org.roof.web.menu.entity.Menu;
import org.roof.web.menu.service.api.IMenuFilter;
import org.roof.web.resource.dao.api.IResourceDao;
import org.roof.web.resource.entity.Module;
import org.roof.web.resource.entity.Privilege;
import org.roof.web.resource.entity.Resource;
import org.roof.web.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

/**
 * 
 * @author liuxin
 * 
 */
@Service
public class MenuFilter implements IMenuFilter {

	private SecurityMetadataSource securityMetadataSource;
	private AccessDecisionManager accessDecisionManager;
	private IMenuDao menuDao;
	private IResourceDao resourceDao;

	@Override
	public Menu doFilter(Long id, Authentication authentication) {
		Menu menu = menuDao.load(id);
		loadMenuModule(menu);
		return doFilter(menu, authentication);
	}

	private void loadMenuModule(Menu menu) {
		if (menu.getModule() != null && menu.getModule().getId() != null) {
			menu.setModule(resourceDao.load(Privilege.class, menu.getModule().getId()));
		}
	}

	@Override
	public Menu doFilter(Menu menu, Authentication authentication) {
		List<Menu> menus = doFilter(menuDao.findMenuByParent(menu.getId(), menu.getMenuType()), authentication);
		if (menus != null) {
			for (Menu menu2 : menus) {
				loadMenuModule(menu2);
				doFilter(menu2, authentication);
			}
			menu.setChildren(menus);
		}
		if (CollectionUtils.isEmpty(menu.getChildren()) && menu.getModule() == null) {
			return null;
		}
		return menu;
	}

	@Override
	public List<Menu> doFilter(List<Menu> menus, Authentication authentication) {
		List<Menu> result = new ArrayList<Menu>();
		if (authentication == null) {
			return null;
		}
		for (Menu menu : menus) {
			loadMenuModule(menu);
			Module module = menu.getModule();
			if (module == null) {
				result.add(menu);
				continue;
			}
			try {
				String path = menu.getModule().getPath();
				Collection<ConfigAttribute> configAttributes = securityMetadataSource.getAttributes(path);
				accessDecisionManager.decide(authentication, path, configAttributes);
				result.add(menu);
			} catch (InsufficientAuthenticationException e) {
			} catch (AccessDeniedException e) {
			}
		}
		return result;
	}

	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	public List<Menu> filterResource(List<Menu> menus, User user) {
		List<Resource> r = (List<Resource>) resourceDao.selectByUser(user.getId());
		List<Menu> result = new ArrayList<Menu>();
		for (Menu menuVo : menus) {
			String url = menuVo.getModule().getPath();
			for (Resource resource : r) {
				String pattern = resource.getPattern();
				if (pathMatcher.match(pattern, url)) {
					result.add(menuVo);
					break;
				}
			}
		}
		return result;
	}

	public IMenuDao getMenuDao() {
		return menuDao;
	}

	public IResourceDao getResourceDao() {
		return resourceDao;
	}

	@Autowired
	public void setSecurityMetadataSource(
			@Qualifier("securityMetadataSource") SecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}

	@Autowired
	public void setAccessDecisionManager(
			@Qualifier("accessDecisionManager") AccessDecisionManager accessDecisionManager) {
		this.accessDecisionManager = accessDecisionManager;
	}

	@Autowired
	public void setMenuDao(@Qualifier("menuDao") IMenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@Autowired
	public void setResourceDao(@Qualifier("resourceDao") IResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

}
