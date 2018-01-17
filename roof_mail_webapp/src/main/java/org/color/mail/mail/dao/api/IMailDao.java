package org.color.mail.mail.dao.api;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;

import org.color.mail.mail.entity.Mail;

public interface IMailDao extends IDaoSupport {
	Page page(Page page, Mail mail);
}