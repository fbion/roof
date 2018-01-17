package org.roof.web.resource.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.web.resource.dao.api.IQueryFilterResourceDao;
import org.roof.web.resource.entity.QueryFilterResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 
 * @author liuxin
 * 
 */
@Service
public class QueryFilterResourceDao extends AbstractDao implements
		IQueryFilterResourceDao {
	/**
	 * 获取指定角色下查询的过滤
	 * 
	 * @param path
	 *            查询的路径
	 * @param roleIds
	 *            角色列表
	 * @return 过滤列表
	 */
	public List<QueryFilterResource> findByPath(String path, Long[] roleIds) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put("path", path);
		parameterObject.put("roleIds", roleIds);
		@SuppressWarnings("unchecked")
		List<QueryFilterResource> result = (List<QueryFilterResource>) findByMappedQuery(
				"org.roof.web.resource.dao.impl.QueryFilterResourceDao.findByPath",
				parameterObject);
		return result;
	}

	@Override
	@Autowired(required = true)
	public void setDaoSupport(
			@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}
}
