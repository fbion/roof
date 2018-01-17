package org.color.mail.mail.entity;

import java.util.List;

/**
 * @author 模版生成 <br/>
 *         表名： t_mail <br/>
 *         描述：t_mail <br/>
 */
public class MailVo extends Mail {

	private List<MailVo> mailList;

	public MailVo() {
		super();
	}

	public MailVo(Long id) {
		super();
		this.id = id;
	}

	public List<MailVo> getMailList() {
		return mailList;
	}

	public void setMailList(List<MailVo> mailList) {
		this.mailList = mailList;
	}

}
