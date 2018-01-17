package org.color.mail.faillog.service.impl;

import java.io.Serializable;
import java.util.List;
import org.roof.roof.dataaccess.api.Page;
import org.color.mail.faillog.dao.api.IFailLogDao;
import org.color.mail.faillog.entity.FailLog;
import org.color.mail.faillog.entity.FailLogVo;
import org.color.mail.faillog.service.api.IFailLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FailLogService implements IFailLogService {
	private IFailLogDao failLogDao;

	public Serializable save(FailLog failLog){
		return failLogDao.save(failLog);
	}

	public void delete(FailLog failLog){
		failLogDao.delete(failLog);
	}
	
	public void deleteByExample(FailLog failLog){
		failLogDao.deleteByExample(failLog);
	}

	public void update(FailLog failLog){
		failLogDao.update(failLog);
	}
	
	public void updateIgnoreNull(FailLog failLog){
		failLogDao.updateIgnoreNull(failLog);
	}
		
	public void updateByExample(FailLog failLog){
		failLogDao.update("updateByExampleFailLog", failLog);
	}

	public FailLogVo load(FailLog failLog){
		return (FailLogVo)failLogDao.reload(failLog);
	}
	
	public List<FailLogVo> selectForList(FailLog failLog){
		return (List<FailLogVo>)failLogDao.selectForList("selectFailLog",failLog);
	}
	
	public Page page(Page page, FailLog failLog) {
		return failLogDao.page(page, failLog);
	}

	@Autowired
	public void setIFailLogDao(
			@Qualifier("failLogDao") IFailLogDao  failLogDao) {
		this.failLogDao = failLogDao;
	}

}
