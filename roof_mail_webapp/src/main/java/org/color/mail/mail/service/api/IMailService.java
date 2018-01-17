package org.color.mail.mail.service.api;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import java.io.Serializable;

import org.roof.roof.dataaccess.api.Page;
import org.roof.spring.Result;
import org.color.mail.mail.entity.Mail;
import org.color.mail.mail.entity.MailVo;
import org.color.mail.mailuser.entity.MailUser;

public interface IMailService {

	/**
	 * 将对象保存，返回该条记录的操作数量，保存成功之后，将主键填充到参数对象中
	 */
	public abstract Serializable save(Mail mail);

	/**
	 * 按对象中的主键进行删除，如果是DRDS，还需要添加拆分键
	 */
	public abstract void delete(Mail mail);

	/**
	 * 按对象中的非空属性作为条件，进行删除
	 */
	public abstract void deleteByExample(Mail mail);

	/**
	 * 按对象中的主键进行所有属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void update(Mail mail);

	/**
	 * 按对象中的主键进行所有非空属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void updateIgnoreNull(Mail mail);

	/**
	 * 按对象中的非空属性作为条件，进行修改
	 */
	public abstract void updateByExample(Mail mail);

	/**
	 * 按对象中的主键进行数据加载，如果是DRDS，还需要添加拆分键
	 */
	public abstract MailVo load(Mail mail);

	/**
	 * 按对象中的非空属性作为条件，进行查询
	 */
	public abstract List<MailVo> selectForList(Mail mail);

	/**
	 * 按对象中的非空属性作为条件，进行分页查询
	 */
	public abstract Page page(Page page, Mail mail);

	public Result testSend(String to, Long mailId);

	public void send(MailUser user, Mail mail);

	public void send(String to, String subject, String text) throws MessagingException;

	public void sendBatch(Long id);

	public void sendBatchTask(final Long mailId, Date startTime);

	public void sendBatchSchedule(final Long mailId, Date date);

	public void sendBatch(Mail mail, Collection<MailUser> mailUsers);

}