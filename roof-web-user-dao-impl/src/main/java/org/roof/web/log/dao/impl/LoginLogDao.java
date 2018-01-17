package org.roof.web.log.dao.impl;

import org.roof.dataaccess.PageQuery;
import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.IPageQuery;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageQueryFactory;
import org.roof.web.log.dao.api.ILoginLogDao;
import org.roof.web.log.entity.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
public class LoginLogDao extends AbstractDao implements ILoginLogDao {
	private PageQueryFactory<PageQuery> pageQueryFactory;
	
	public Page page(Page page, LoginLog loginLog) {
		IPageQuery pageQuery = pageQueryFactory.getPageQuery(page,"selectLoginLogPage", "selectLoginLogCount");
		//IPageQuery pageQuery = pageQueryFactory.getPageQuery(page,"selectLoginLogPage", null);
		return pageQuery.select(loginLog);
	}

	@Autowired
	public void setDaoSupport(
			@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}
	
	@Autowired
	public void setPageQueryFactory(
			@Qualifier("pageQueryFactory") PageQueryFactory<PageQuery> pageQueryFactory) {
		this.pageQueryFactory = pageQueryFactory;
	}

}