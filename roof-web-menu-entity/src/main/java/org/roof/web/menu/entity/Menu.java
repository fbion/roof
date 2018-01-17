package org.roof.web.menu.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;

import org.roof.web.resource.entity.Module;

public class Menu implements Serializable {
	private static final long serialVersionUID = 7309864170153025694L;
	private Long id;
	private String dtype = this.getClass().getSimpleName();
	private String name;
	private Integer lvl;
	private Boolean leaf;
	private Menu parent;
	private List<Menu> children;
	private Module module;
	private String target;
	private MenuType menuType;
	private String script;
	private String icon;
	private Integer seq;

	public Menu() {
		super();
	}

	public Menu(Long id) {
		super();
		this.id = id;
	}

	@Id // 主键
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getLvl() {
		return lvl;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public Menu getParent() {
		return parent;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public Module getModule() {
		return module;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLvl(Integer lvl) {
		this.lvl = lvl;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public MenuType getMenuType() {
		return menuType;
	}

	public void setMenuType(MenuType menuType) {
		this.menuType = menuType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

}
