package org.roof.dataaccess;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageQueryFactory;

public class FastPageQueryFactory implements PageQueryFactory<FastPageQuery> {
	private IDaoSupport daoSupport;

	@Override
	public FastPageQuery getPageQuery() {
		return new FastPageQuery(daoSupport);
	}

	@Override
	public FastPageQuery getPageQuery(Page page, String queryStr,
			String countStr) {
		FastPageQuery fastPageQuery = new FastPageQuery(daoSupport);
		fastPageQuery.setPage(page);
		fastPageQuery.setQueryStr(queryStr);
		fastPageQuery.setCountStr(countStr);
		return fastPageQuery;
	}

	public IDaoSupport getDaoSupport() {
		return daoSupport;
	}

	public void setDaoSupport(IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

}
