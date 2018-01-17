package org.roof.web.dictionary.entity;

import java.io.Serializable;

import javax.persistence.Id;

/**
 * 字典表
 * 
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 Dictionary.java 2011-11-4
 */
public class Dictionary implements Serializable {

	private static final long serialVersionUID = 8373619199358728370L;
	protected Long id;
	protected String type; // 类型(组)
	protected String val; // 值
	protected String text; // 文本
	protected Long seq;// 排序
	protected String active;// 是否激活
	protected String description; // 描述
	protected String exp1; // 预留1
	protected String exp2; // 预留2

	public Dictionary() {
	}

	public Dictionary(Long id) {
		super();
		this.id = id;
	}

	public Dictionary(Long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	@Id
	public Long getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getText() {
		return text;
	}

	public String getVal() {
		return val;
	}

	public String getActive() {
		return active;
	}

	public String getDescription() {
		return description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getExp1() {
		return exp1;
	}

	public void setExp1(String exp1) {
		this.exp1 = exp1;
	}

	public String getExp2() {
		return exp2;
	}

	public void setExp2(String exp2) {
		this.exp2 = exp2;
	}

}
