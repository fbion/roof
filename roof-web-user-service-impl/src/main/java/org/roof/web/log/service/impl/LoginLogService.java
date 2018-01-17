package org.roof.web.log.service.impl;

import java.io.Serializable;
import java.util.List;

import org.roof.roof.dataaccess.api.Page;
import org.roof.web.log.dao.api.ILoginLogDao;
import org.roof.web.log.entity.LoginLog;
import org.roof.web.log.entity.LoginLogVo;
import org.roof.web.log.service.api.ILoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 登录日志
 * 
 * @author liuxin
 *
 */
@Service
public class LoginLogService implements ILoginLogService {
	private ILoginLogDao loginLogDao;

	public Serializable save(LoginLog loginLog) {
		return loginLogDao.save(loginLog);
	}

	public void delete(LoginLog loginLog) {
		loginLogDao.delete(loginLog);
	}

	public void deleteByExample(LoginLog loginLog) {
		loginLogDao.deleteByExample(loginLog);
	}

	public void update(LoginLog loginLog) {
		loginLogDao.update(loginLog);
	}

	public void updateIgnoreNull(LoginLog loginLog) {
		loginLogDao.updateIgnoreNull(loginLog);
	}

	public void updateByExample(LoginLog loginLog) {
		loginLogDao.update("updateByExampleLoginLog", loginLog);
	}

	public LoginLogVo load(LoginLog loginLog) {
		return (LoginLogVo) loginLogDao.reload(loginLog);
	}

	public List<LoginLogVo> selectForList(LoginLog loginLog) {
		return (List<LoginLogVo>) loginLogDao.selectForList("selectLoginLog", loginLog);
	}

	public Page page(Page page, LoginLog loginLog) {
		return loginLogDao.page(page, loginLog);
	}

	@Autowired
	public void setLoginLogDao(@Qualifier("loginLogDao") ILoginLogDao loginLogDao) {
		this.loginLogDao = loginLogDao;
	}

}
