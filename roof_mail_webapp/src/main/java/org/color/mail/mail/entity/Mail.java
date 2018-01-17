package org.color.mail.mail.entity;

import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 模版生成 <br/>
 *         表名： t_mail <br/>
 *         描述：t_mail <br/>
 */
public class Mail implements Serializable {
	// 需要手动添加非默认的serialVersionUID
	protected Long id;// id
	protected String name;// 名称
	protected String content;// 内容
	protected Integer send_count;// 发送次数
	protected Integer stat;// 状态

	public Mail() {
		super();
	}

	public Mail(Long id) {
		super();
		this.id = id;
	}
	
	@Id// 主键
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getSend_count() {
		return send_count;
	}
	public void setSend_count(Integer send_count) {
		this.send_count = send_count;
	}
	
	public Integer getStat() {
		return stat;
	}
	public void setStat(Integer stat) {
		this.stat = stat;
	}
}
