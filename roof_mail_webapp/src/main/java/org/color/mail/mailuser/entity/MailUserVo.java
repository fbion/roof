package org.color.mail.mailuser.entity;

import java.util.List;

/**
 * @author 模版生成 <br/>
 *         表名： t_mail_user <br/>
 *         描述：t_mail_user <br/>
 */
public class MailUserVo extends MailUser {

	private List<MailUserVo> mailUserList;

	public MailUserVo() {
		super();
	}

	public MailUserVo(Long id) {
		super();
		this.id = id;
	}

	public List<MailUserVo> getMailUserList() {
		return mailUserList;
	}

	public void setMailUserList(List<MailUserVo> mailUserList) {
		this.mailUserList = mailUserList;
	}

}
