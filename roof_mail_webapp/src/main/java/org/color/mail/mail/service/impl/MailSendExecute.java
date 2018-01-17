package org.color.mail.mail.service.impl;

import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.color.mail.mail.entity.Mail;
import org.color.mail.mail.service.api.IMailService;
import org.color.mail.mailuser.entity.MailUser;
import org.color.mail.mailuser.service.api.IMailUserService;
import org.roof.commons.RoofDateUtils;
import org.roof.roof.dataaccess.api.Page;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

public class MailSendExecute {

	private ConcurrentTaskScheduler taskExecutor;
	private Logger logger = Logger.getLogger(MailSendExecute.class);
	private long send_interval = 0; // 发送间隔
	private Mail mail;
	private IMailService mailService;
	private IMailUserService mailUserService;

	public MailSendExecute(ConcurrentTaskScheduler taskExecutor, long send_interval, Mail mail,
			IMailService mailService, IMailUserService mailUserService) {
		super();
		this.taskExecutor = taskExecutor;
		this.send_interval = send_interval;
		this.mail = mail;
		this.mailService = mailService;
		this.mailUserService = mailUserService;
	}

	public void execute(final Page page, Date date, final MailUser params) {
		taskExecutor.schedule(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				mailService.sendBatch(mail, (Collection<MailUser>) page.getDataList());

				if (page.nextPage()) {
					Page nextPage = mailUserService.page(page, params);
					Date nextTime = new Date(System.currentTimeMillis() + send_interval);
					if (logger.isInfoEnabled()) {
						logger.info("下一次批量发送时间"
								+ RoofDateUtils.dateToString(nextTime, RoofDateUtils.DATE_TIME_EXPRESSION_GENERAL));
					}
					execute(nextPage, nextTime, params);
				} else {
					mail.setStat(2);
					mailService.update(mail);
					if (logger.isInfoEnabled()) {
						logger.info("邮件id=[" + mail.getId() + "]name=[" + mail.getName() + "]发送完成");
					}
				}
			}
		}, date);
	}
}
