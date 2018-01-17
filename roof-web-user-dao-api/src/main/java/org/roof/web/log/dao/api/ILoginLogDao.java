package org.roof.web.log.dao.api;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;

import org.roof.web.log.entity.LoginLog;

public interface ILoginLogDao extends IDaoSupport {
	Page page(Page page, LoginLog loginLog);
}