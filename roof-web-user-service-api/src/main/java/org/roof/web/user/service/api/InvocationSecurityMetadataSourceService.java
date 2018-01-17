package org.roof.web.user.service.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.roof.web.resource.dao.api.IResourceDao;
import org.roof.web.resource.entity.Resource;
import org.roof.web.role.dao.api.IRoleDao;
import org.roof.web.role.entity.BaseRole;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

/**
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 InvocationSecurityMetadataSourceService.java 2012-7-5
 */
public class InvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
	private IResourceDao resourceDao;
	private IRoleDao roleDao;

	private static final Logger logger = Logger.getLogger(InvocationSecurityMetadataSourceService.class);

	private CacheManager cacheManager;

	private Map<String, Collection<ConfigAttribute>> resourceMap = null;
	private final AntPathMatcher pathMatcher = new AntPathMatcher();
	private String cachName;

	@SuppressWarnings("unchecked")
	public void loadResourceDefine() {
		resourceMap = null;
		Cache cache = cacheManager.getCache(cachName);
		ValueWrapper valueWrapper = cache.get("resource_map");
		if (valueWrapper != null) {
			resourceMap = (Map<String, Collection<ConfigAttribute>>) valueWrapper.get();
		}
		if (resourceMap != null) {
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("开始加载权限...!");
		}
		this.resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		List<Resource> resources = (List<Resource>) resourceDao.loadAll();
		for (Resource resource : resources) {
			String key = resource.getPattern();
			if (StringUtils.isEmpty(key)) {
				continue;
			}
			Collection<ConfigAttribute> atts;
			if (resourceMap.containsKey(key)) {
				atts = resourceMap.get(key);
			} else {
				atts = new ArrayList<ConfigAttribute>();
			}
			for (BaseRole role : roleDao.selectByResource(resource.getId())) {
				ConfigAttribute ca = new SecurityConfig(role.getAuthority());
				atts.add(ca);
				if (logger.isDebugEnabled()) {
					logger.debug("获得角色：[" + role.getAuthority() + " " + role.getAuthority() + "]拥有：[" + resource.getId()
							+ " " + resource.getPattern() + "]的权限!");
				}
			}
			resourceMap.put(resource.getPattern(), atts);
		}
		Collection<ConfigAttribute> atts = resourceMap.get("/**/*");
		if (atts == null) {
			atts = new ArrayList<ConfigAttribute>();
		}
		atts.add(new SecurityConfig("ROLE_-1"));
		resourceMap.put("/**/*", atts);
		cache.put("resource_map", resourceMap);
		if (logger.isDebugEnabled()) {
			logger.debug("权限加载完成!");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String url = null;
		if (object instanceof FilterInvocation) {
			url = ((FilterInvocation) object).getRequestUrl();
		}
		if (object instanceof String) {
			url = (String) object;
		}
		Collection<ConfigAttribute> result = null;
		Cache cache = cacheManager.getCache(cachName);
		ValueWrapper valueWrapper = cache.get(url);
		if (valueWrapper != null) {
			result = (Collection<ConfigAttribute>) valueWrapper.get();
		} 
		if (result != null) {
			return result;
		}
		loadResourceDefine();
		result = new ArrayList<ConfigAttribute>();
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String pattern = ite.next();
			if (pathMatcher.match(pattern, url)) {
				Collection<ConfigAttribute> configAttrs = resourceMap.get(pattern);
				if (CollectionUtils.isNotEmpty(configAttrs)) {
					result.addAll(configAttrs);
				}
			}
		}
		cache.put(url, result);
		return result;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		loadResourceDefine();
		Collection<ConfigAttribute> result = new ArrayList<ConfigAttribute>();
		for (Collection<ConfigAttribute> configAttribute : resourceMap.values()) {
			result.addAll(configAttribute);
		}
		return result;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public String getCachName() {
		return cachName;
	}

	public void setCachName(String cachName) {
		this.cachName = cachName;
	}

	public IResourceDao getResourceDao() {
		return resourceDao;
	}

	public void setResourceDao(IResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

}
