package org.roof.web.dictionary.entity;

import java.io.Serializable;
import java.util.List;

public class DictionaryVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 668866250619896958L;
	private String id;
	private String pId;
	private String name;
	private String title;
	private Boolean leaf;
	private String type;
	private String url;
	private String icon;
	private List<DictionaryVo> children;
	private Boolean checked;
	private String val;
	private String description; // 描述

	public DictionaryVo() {
		super();
	}

	public DictionaryVo(Long id, String name, Boolean leaf, String type, String url, Long pId) {
		super();
		this.id = id.toString();
		this.name = name;
		this.leaf = leaf;
		this.type = type;
		this.url = url;
		this.pId = pId.toString();
	}

	public DictionaryVo(String id, String name, String title, String url, String icon) {
		super();
		this.id = id;
		this.name = name;
		this.title = title;
		this.url = url;
		this.icon = icon;
	}

	public DictionaryVo(String id, String pId) {
		super();
		this.id = id;
		this.pId = pId;
	}

	public String getId() {
		return id;
	}

	public String getPId() {
		return pId;
	}

	public String getName() {
		return name;
	}

	public List<DictionaryVo> getChildren() {
		return children;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPId(String pId) {
		this.pId = pId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChildren(List<DictionaryVo> children) {
		this.children = children;
	}

	public Boolean getIsParent() {
		if (leaf == null) {
			return true;
		}
		return !leaf;
	}

	public void setIsParent(Boolean isParent) {
		this.leaf = !isParent;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
