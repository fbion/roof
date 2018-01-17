package org.roof.web.org.entity;

import java.io.Serializable;

/**
 * 组织架构
 * 
 * @author liuxin
 * 
 */
public class Organization implements Serializable {
	private static final long serialVersionUID = -6499305660200043952L;
	protected Long id;// id
	protected String name;// 组织名称
	protected String code;// 组织代码
	protected Long parent_id;// 父节点@PARENT_ID
	protected Integer lvl; // 层级
	protected Integer seq;// 排序
	protected Boolean leaf;// 是否为叶子节点
	protected Boolean usable; // 是否可用(逻辑删除)

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

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public Integer getLvl() {
		return lvl;
	}

	public void setLvl(Integer lvl) {
		this.lvl = lvl;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Boolean getUsable() {
		return usable;
	}

	public void setUsable(Boolean usable) {
		this.usable = usable;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
