package org.color.mail.faillog.dao.impl;

import org.roof.dataaccess.FastPageQuery;
import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.color.mail.faillog.entity.FailLog;
import org.color.mail.faillog.dao.api.IFailLogDao;
import org.roof.roof.dataaccess.api.FastPage;
import org.roof.roof.dataaccess.api.IPageQuery;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
public class FailLogDao extends AbstractDao implements IFailLogDao {
	private PageQueryFactory<FastPageQuery> pageQueryFactory;
	
	public Page page(Page page, FailLog failLog) {
		IPageQuery pageQuery = pageQueryFactory.getPageQuery(page,"selectFailLogPage", "selectFailLogCount");
		//IPageQuery pageQuery = pageQueryFactory.getPageQuery(page,"selectFailLogPage", null);
		return pageQuery.select(failLog);
	}

	@Autowired
	public void setDaoSupport(
			@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}
	
	@Autowired
	public void setPageQueryFactory(
			@Qualifier("fastPageQueryFactory") PageQueryFactory<FastPageQuery> pageQueryFactory) {
		this.pageQueryFactory = pageQueryFactory;
	}

}