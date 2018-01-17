package org.roof.web.resource.entity;

/**
 * 模块
 * 
 * @author liuxin
 * 
 */
public class Module extends Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1719946423105131558L;
	protected String identify; // 标识
	protected String path;// 路径
	protected Integer seq;// 顺序
	protected Integer lvl;// 等级
	protected Module parent; // 父节点
	protected Boolean leaf;// 是否为叶子节点

	public String getIdentify() {
		return identify;
	}

	public String getPath() {
		return path;
	}

	public Integer getSeq() {
		return seq;
	}

	public Integer getLvl() {
		return lvl;
	}

	public Module getParent() {
		return parent;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public void setLvl(Integer lvl) {
		this.lvl = lvl;
	}

	public void setParent(Module parent) {
		this.parent = parent;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

}
