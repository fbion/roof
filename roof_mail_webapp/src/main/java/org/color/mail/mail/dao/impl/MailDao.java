package org.color.mail.mail.dao.impl;

import org.roof.dataaccess.FastPageQuery;
import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.color.mail.mail.entity.Mail;
import org.color.mail.mail.dao.api.IMailDao;
import org.roof.roof.dataaccess.api.FastPage;
import org.roof.roof.dataaccess.api.IPageQuery;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
public class MailDao extends AbstractDao implements IMailDao {
	private PageQueryFactory<FastPageQuery> pageQueryFactory;
	
	public Page page(Page page, Mail mail) {
		IPageQuery pageQuery = pageQueryFactory.getPageQuery(page,"selectMailPage", "selectMailCount");
		//IPageQuery pageQuery = pageQueryFactory.getPageQuery(page,"selectMailPage", null);
		return pageQuery.select(mail);
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