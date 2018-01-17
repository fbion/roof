package org.color.mail.mailuser.dao.api;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;

import org.color.mail.mailuser.entity.MailUser;

public interface IMailUserDao extends IDaoSupport {
	Page page(Page page, MailUser mailUser);

	public int cancel(String mailMd5);
}