package org.roof.web.resource.dao.api;

import java.util.List;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.web.resource.entity.QueryResource;

public interface IQueryResourceDao extends IDaoSupport {

	public abstract QueryResource findByIdentify(String identify);

	public abstract List<QueryResource> findAll();

}