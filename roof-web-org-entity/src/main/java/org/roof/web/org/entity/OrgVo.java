package org.roof.web.org.entity;

import java.io.Serializable;
import java.util.List;

public class OrgVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8025951971987852493L;
	private String id;
	private String pId;
	private String name;
	private String title;
	private Boolean leaf;
	private String type;
	private String url;
	private String icon;
	private List<OrgVo> children;
	private Boolean checked;
	private Integer lvl; // 层级

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public List<OrgVo> getChildren() {
		return children;
	}

	public void setChildren(List<OrgVo> children) {
		this.children = children;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Integer getLvl() {
		return lvl;
	}

	public void setLvl(Integer lvl) {
		this.lvl = lvl;
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

}
