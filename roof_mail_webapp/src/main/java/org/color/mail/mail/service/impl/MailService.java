package org.color.mail.mail.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.color.mail.faillog.entity.FailLog;
import org.color.mail.faillog.service.api.IFailLogService;
import org.color.mail.mail.dao.api.IMailDao;
import org.color.mail.mail.entity.Mail;
import org.color.mail.mail.entity.MailVo;
import org.color.mail.mail.service.api.IMailService;
import org.color.mail.mailuser.dao.api.IMailUserDao;
import org.color.mail.mailuser.entity.MailUser;
import org.color.mail.mailuser.service.api.IMailUserService;
import org.roof.commons.PropertiesUtil;
import org.roof.roof.dataaccess.api.Page;
import org.roof.spring.Result;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class MailService implements IMailService, InitializingBean {
	private IMailDao mailDao;
	private JavaMailSender mailSender;
	private List<JavaMailSender> mailSenders;
	private IMailUserDao mailUserDao;
	private IMailUserService mailUserService;
	private Logger logger = Logger.getLogger(MailService.class);
	private ConcurrentTaskScheduler taskExecutor;
	private IFailLogService failLogService;
	private long sendTimes = 0;
	private long send_interval = 0; // 发送间隔
	private long send_number = 0; // 一次发送数量
	private int index; // 执行mailsender的序列

	private static String url = null;

	public void sendBatchTask(final Long mailId, Long mailNumber, Long interval, Date startTime)
			throws InterruptedException {
		Mail mail = new Mail(mailId);
		mail = load(mail);
		mail.setStat(1);
		update(mail);
		MailUser mailUser = new MailUser();
		mailUser.setEnabled(true);
		Page page = new Page(0L, mailNumber);
		page = mailUserService.page(page, mailUser);
		MailSendExecute mailSendExecute = new MailSendExecute(taskExecutor, send_interval, mail, this, mailUserService);
		mailSendExecute.execute(page, new Date(startTime.getTime() + (1 * 1000)), mailUser);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		url = PropertiesUtil.getPorpertyString("project.webUrl");
		send_interval = Long.parseLong(PropertiesUtil.getPorpertyString("mail.send_interval")) * 1000;
		send_number = Long.parseLong(PropertiesUtil.getPorpertyString("mail.send_number"));
	}

	public Serializable save(Mail mail) {
		mail.setSend_count(0);
		mail.setStat(0);
		return mailDao.save(mail);
	}

	public void delete(Mail mail) {
		mailDao.delete(mail);
	}

	public void deleteByExample(Mail mail) {
		mailDao.deleteByExample(mail);
	}

	public void update(Mail mail) {
		mailDao.update("org.color.mail.mail.dao.updateIgnoreNullMail", mail);
	}

	public void updateIgnoreNull(Mail mail) {
		mailDao.updateIgnoreNull(mail);
	}

	public void updateByExample(Mail mail) {
		mailDao.update("updateByExampleMail", mail);
	}

	public MailVo load(Mail mail) {
		return (MailVo) mailDao.reload(mail);
	}

	public List<MailVo> selectForList(Mail mail) {
		return (List<MailVo>) mailDao.selectForList("selectMail", mail);
	}

	public Page page(Page page, Mail mail) {
		return mailDao.page(page, mail);
	}

	public void sendBatchTask(final Long mailId, Date startTime) {
		try {
			this.sendBatchTask(mailId, send_number, send_interval, startTime);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void sendBatchSchedule(final Long mailId, Date starttime) {
		try {
			sendBatchTask(mailId, this.send_number, this.send_interval, starttime);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void sendBatchSchedule(final Long mailId, final List<MailUser> mailUsers, Date date) {

		final Mail mail = mailDao.load(Mail.class, mailId);
		taskExecutor.schedule(new Runnable() {

			@Override
			public void run() {
				sendBatch(mail, mailUsers);
			}
		}, date);
	}

	public void sendBatch(Long mailId) {
		Mail mail = new Mail(mailId);
		mail = load(mail);
		mail.setStat(1);
		update(mail);
		MailUser mailUser = new MailUser();
		mailUser.setEnabled(true);
		Page page = new Page(0L, this.send_number);
		page = mailUserService.page(page, mailUser);
		sendBatch(mail, (Collection<MailUser>) page.getDataList());
		while (page.nextPage()) {
			page.setCurrentPage(page.getCurrentPage() + 1);
			page = mailUserService.page(page, mailUser);
			sendBatch(mail, (Collection<MailUser>) page.getDataList());
		}
		mail.setStat(2);
		update(mail);
	}

	public void sendBatch(Mail mail, Collection<MailUser> mailUsers) {
		if (logger.isInfoEnabled()) {
			logger.info("[开始]批量发送执行" + mailUsers.size() + "用户");
		}
		for (MailUser mailUser : mailUsers) {
			send(mailUser, mail);
		}
		if (logger.isInfoEnabled()) {
			logger.info("[结束]批量发送执行完毕发送" + mailUsers.size() + "用户");
		}
	}

	public Result testSend(String to, Long mailId) {
		Mail mail = this.load(new Mail(mailId));
		try {
			this.send(to, mail.getName(), mail.getContent());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new Result(e);
		}
		return new Result("发送成功");
	}

	public void send(MailUser user, Mail mail) {
		FailLog failLog = new FailLog();
		failLog.setMail(user.getMail());
		failLog.setMail_id(mail.getId());
		failLog.setCreate_time(new Date());
		try {
			String content = mail.getContent();
			if (user.getMailMD5() != null) {
				String r = StringUtils.replaceOnce(url, "http://", "");
				String cancel = r.concat("/mail/mailuserAction/cancel_page/").concat(user.getMailMD5())
						.concat(".action");
				String subscribe = r.concat("/mail/mailuserAction/subscribe_page/").concat(user.getMail())
						.concat(".action");
				content = StringUtils.replaceEach(content, new String[] { "${cancel}", "${subscribe}" },
						new String[] { cancel, subscribe });
			}
			send(user.getMail(), mail.getName(), content);
			failLog.setMsg(Result.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			user.setFail_count(user.getFail_count() == null ? 0 : user.getFail_count() + 1);
			// if (user.getFail_count() >= 3) {
			// user.setEnabled(false);
			// }
			failLog.setMsg(e.getMessage());
		} finally {
			mail.setSend_count(mail.getSend_count() + 1);
			this.update(mail);
			mailUserService.update(user);
			failLogService.save(failLog);
		}
	}

	public void send(String to, String subject, String text) throws MessagingException {
		sendTimes += 1;
		JavaMailSenderImpl currentMailSender = (JavaMailSenderImpl) findMailSender();

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(to);
		helper.setText("<html><head><meta http-equiv=" + "Content-Type" + " content=" + "text/html; charset=utf-8"
				+ "></head><body>" + text + "</body></html>", true);
		helper.setSubject(subject);
		helper.setFrom(currentMailSender.getUsername());
		currentMailSender.send(message);
		if (logger.isInfoEnabled()) {
			logger.info("to[" + to + "]发送完成");
		}
	}

	private JavaMailSender findMailSender() {
		if (CollectionUtils.isEmpty(mailSenders)) {
			return mailSender;
		}
		int current = (int) ((sendTimes / mailSenders.size()) % mailSenders.size());
		if (current != index) {
			if (logger.isInfoEnabled()) {
				logger.info("邮箱更换" + index + "->" + current + "休眠1分钟");
			}
			index = current;
			try {
				Thread.sleep(1000 * 60);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
			if (logger.isInfoEnabled()) {
				logger.info("邮箱更换[" + index + "]完成,重新开始发送");
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("邮箱[" + index + "]正在发送");
		}
		return mailSenders.get(index);
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	@Autowired
	public void setIMailDao(@Qualifier("mailDao") IMailDao mailDao) {
		this.mailDao = mailDao;
	}

	@Autowired
	public void setMailSender(@Qualifier("mailSender") JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Autowired
	public void setMailUserDao(@Qualifier("mailUserDao") IMailUserDao mailUserDao) {
		this.mailUserDao = mailUserDao;
	}

	@Autowired
	public void setMailUserService(@Qualifier("mailUserService") IMailUserService mailUserService) {
		this.mailUserService = mailUserService;
	}

	@Autowired
	public void setTaskExecutor(
			@Qualifier("taskExecutor") org.springframework.scheduling.concurrent.ConcurrentTaskScheduler taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	@Autowired
	public void setFailLogService(@Qualifier("failLogService") IFailLogService failLogService) {
		this.failLogService = failLogService;
	}

	@Resource(name = "mailSenders")
	public void setMailSenders(List<JavaMailSender> mailSenders) {
		this.mailSenders = mailSenders;
	}

}
