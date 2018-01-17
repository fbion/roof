package org.roof.web.resource.dao.api;

import java.util.List;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.web.resource.entity.QueryFilterResource;

public interface IQueryFilterResourceDao extends IDaoSupport {
	public List<QueryFilterResource> findByPath(String path, Long[] roleIds);
}