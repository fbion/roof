package org.roof.web.menu.entity;

import java.io.Serializable;
import java.util.List;

public class MenuVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2750720857030173273L;
	private String id;
	private String pId;
	private String name;
	private String title;
	private Boolean leaf;
	private String type;
	private String url;
	private String icon;
	private List<MenuVo> children;
	private Boolean checked;

	public MenuVo() {
		super();
	}

	public MenuVo(String name, String url, String icon) {
		super();
		this.name = name;
		this.url = url;
		this.icon = icon;
	}

	public MenuVo(String id, String name, String title, String url, String icon) {
		super();
		this.id = id;
		this.name = name;
		this.title = title;
		this.url = url;
		this.icon = icon;
	}

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

	public List<MenuVo> getChildren() {
		return children;
	}

	public void setChildren(List<MenuVo> children) {
		this.children = children;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
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
