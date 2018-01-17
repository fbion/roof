package org.color.mail.mailuser.service.impl;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.color.mail.mail.service.impl.MailService;
import org.color.mail.mailuser.dao.api.IMailUserDao;
import org.color.mail.mailuser.entity.MailUser;
import org.color.mail.mailuser.entity.MailUserVo;
import org.color.mail.mailuser.service.api.IMailUserService;
import org.roof.commons.Md5Generator;
import org.roof.excel.config.XslDb;
import org.roof.excel.core.ExcelDao;
import org.roof.excel.core.ExcelReader;
import org.roof.excel.support.MybatisDatabaseWriter;
import org.roof.excel.support.PoiExcelMappingReader;
import org.roof.roof.dataaccess.api.Page;
import org.roof.spring.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MailUserService implements IMailUserService {
	private IMailUserDao mailUserDao;
	private ExcelDao excelDao;
	private XslDb xslDb;
	private Logger logger = Logger.getLogger(MailUserService.class);

	public Result importByXls(InputStream is) {
		MybatisDatabaseWriter databaseWriter = new MybatisDatabaseWriter(excelDao);
		ExcelReader excelReader = new PoiExcelMappingReader(is, xslDb);
		int i = 1;
		while (excelReader.hasNext()) {
			try {
				Map<String, Object> map = (Map<String, Object>) excelReader.next();
				String mail = ObjectUtils.toString(map.get("mail"), null);
				if (!MailService.checkEmail(mail)) {
					continue;
				}
				String username = ObjectUtils.toString(map.get("username"), null);
				MailUser mailUser = new MailUser();
				mailUser.setMail(mail);
				List<MailUserVo> mailUsers = selectForList(mailUser);
				if (CollectionUtils.isEmpty(mailUsers)) {
					mailUser.setUsername(username);
					mailUser.setMail(mail);
					mailUser.setEnabled(true);
					mailUser.setFail_count(0);
					mailUser.setMailMD5(new Md5Generator().calcmd5(mailUser.getMail()));
					databaseWriter.write("org.color.mail.mailuser.dao.saveMailUser", mailUser);
				} else {
					for (MailUserVo mailUserVo : mailUsers) {
						mailUserVo.setEnabled(true);
						update(mailUserVo);
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return new Result("第" + i + "行导入失败[" + e.getMessage() + "]");
			}
			i++;
		}
		return new Result("导入成功");
	}

	/**
	 * 退订
	 */
	public void cancel(String mailMD5) {
		mailUserDao.cancel(mailMD5);
	}

	/**
	 * 订阅
	 * 
	 * @param mail
	 */
	public Result subscribe(String mail) {
		if (!MailService.checkEmail(mail)) {
			return new Result("电子邮件格式不正确");
		}
		MailUser mailUser = new MailUser();
		mailUser.setMail(mail);
		List<MailUserVo> mailUsers = selectForList(mailUser);
		if (CollectionUtils.isEmpty(mailUsers)) {
			mailUser.setUsername(mail);
			mailUser.setEnabled(true);
			mailUser.setFail_count(0);
			save(mailUser);
		} else {
			for (MailUserVo mailUserVo : mailUsers) {
				mailUserVo.setEnabled(true);
				update(mailUserVo);
			}
		}
		return new Result("订阅成功");
	}

	public Serializable save(MailUser mailUser) {
		if (mailUser.getMail() != null) {
			mailUser.setMailMD5(new Md5Generator().calcmd5(mailUser.getMail()));
		}
		return mailUserDao.save(mailUser);
	}

	public void delete(MailUser mailUser) {
		mailUserDao.delete(mailUser);
	}

	public void deleteByExample(MailUser mailUser) {
		mailUserDao.deleteByExample(mailUser);
	}

	public void update(MailUser mailUser) {
		if (mailUser.getMail() != null) {
			mailUser.setMailMD5(new Md5Generator().calcmd5(mailUser.getMail()));
		}
		mailUserDao.update("org.color.mail.mailuser.dao.updateIgnoreNullMailUser", mailUser);
	}

	public void updateIgnoreNull(MailUser mailUser) {
		if (mailUser.getMail() != null) {
			mailUser.setMailMD5(new Md5Generator().calcmd5(mailUser.getMail()));
		}
		mailUserDao.updateIgnoreNull(mailUser);
	}

	public void updateByExample(MailUser mailUser) {
		mailUserDao.update("updateByExampleMailUser", mailUser);
	}

	public MailUserVo load(MailUser mailUser) {
		return (MailUserVo) mailUserDao.reload(mailUser);
	}

	public List<MailUserVo> selectForList(MailUser mailUser) {
		return (List<MailUserVo>) mailUserDao.selectForList("selectMailUser", mailUser);
	}

	public Page page(Page page, MailUser mailUser) {
		return mailUserDao.page(page, mailUser);
	}

	@Autowired
	public void setIMailUserDao(@Qualifier("mailUserDao") IMailUserDao mailUserDao) {
		this.mailUserDao = mailUserDao;
	}

	@Autowired
	public void setExcelDao(@Qualifier("mybatisDao") ExcelDao excelDao) {
		this.excelDao = excelDao;
	}

	@Autowired
	public void setXslDb(@Qualifier("ecellXslDb") XslDb xslDb) {
		this.xslDb = xslDb;
	}

}
