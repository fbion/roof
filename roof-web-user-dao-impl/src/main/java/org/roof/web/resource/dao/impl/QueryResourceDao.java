package org.roof.web.resource.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.DaoException;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.web.resource.dao.api.IQueryResourceDao;
import org.roof.web.resource.entity.QueryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 
 * @author liuxin
 * 
 */
@Service
public class QueryResourceDao extends AbstractDao implements IQueryResourceDao {

	private static final Logger LOGGER = Logger
			.getLogger(QueryResourceDao.class);

	public QueryResource findByIdentify(String identify) {
		String hql = "from QueryResource a left join fetch a.parent where a.identify = ?";
		QueryResource queryResource = null;
		try {
			queryResource = (QueryResource) findSingle(hql, identify);
		} catch (DaoException e) {
			LOGGER.error(e);
		}
		return queryResource;
	}

	@SuppressWarnings("unchecked")
	public List<QueryResource> findAll() {
		String hql = "from QueryResource q left join fetch q.baseRole where q.class != 'query_filter'";
		return (List<QueryResource>) findForList(hql);
	}

	@Override
	@Autowired(required = true)
	public void setDaoSupport(
			@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

}
