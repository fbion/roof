package org.color.mail.faillog.dao.api;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;

import org.color.mail.faillog.entity.FailLog;

public interface IFailLogDao extends IDaoSupport {
	Page page(Page page, FailLog failLog);
}