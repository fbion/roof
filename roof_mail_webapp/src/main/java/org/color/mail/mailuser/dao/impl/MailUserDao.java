package org.color.mail.mailuser.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.color.mail.mailuser.dao.api.IMailUserDao;
import org.color.mail.mailuser.entity.MailUser;
import org.roof.dataaccess.PageQuery;
import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.IPageQuery;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MailUserDao extends AbstractDao implements IMailUserDao {
	private PageQueryFactory<PageQuery> pageQueryFactory;

	public Page page(Page page, MailUser mailUser) {
		IPageQuery pageQuery = pageQueryFactory.getPageQuery(page, "selectMailUserPage", "selectMailUserCount");
		// IPageQuery pageQuery =
		// pageQueryFactory.getPageQuery(page,"selectMailUserPage", null);
		return pageQuery.select(mailUser);
	}

	public int cancel(String mailMd5) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mail_md5", mailMd5);
		return update("org.color.mail.mailuser.dao.updateMailUserCancel", params);
	}

	@Autowired
	public void setDaoSupport(@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

	@Autowired
	public void setPageQueryFactory(@Qualifier("pageQueryFactory") PageQueryFactory<PageQuery> pageQueryFactory) {
		this.pageQueryFactory = pageQueryFactory;
	}

}