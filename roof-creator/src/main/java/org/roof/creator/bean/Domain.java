package org.roof.creator.bean;

import java.util.List;

/**
 * 实体配置类
 */
public class Domain {
	
	/**
	 * DRDS拆分键
	 */
	private String drdsId;

	/**
	 * Action名称，符合资源树的命名规则
	 */
	private String actionName;

	/**
	 * 该系统名称，用于存放WEB路径
	 */
	private String sysName;

	/**
	 * 对应的表名
	 */
	private String tableName;

	/**
	 * 对应的别名
	 */
	private String alias;

	/**
	 * 对应的表描述
	 */
	private String tableDisplay;

	/**
	 * 生成的包名
	 */
	private String packagePath;

	/**
	 * 生成的包名数组,以.分割
	 */
	private List<String> packageArr;

	/**
	 * 生成的类名
	 */
	private String clazzName;

	/**
	 * 生成的文件目录
	 */
	private String fileDir;

	/**
	 * 主键
	 */
	private List<String> primaryKey;

	/**
	 * 属性列表
	 */
	private List<Field> fields;

	/**
	 * 主外键关系列表
	 */
	private List<Relation> relations;

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableDisplay() {
		return tableDisplay;
	}

	public void setTableDisplay(String tableDisplay) {
		this.tableDisplay = tableDisplay;
	}

	public List<String> getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(List<String> primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public List<String> getPackageArr() {
		return packageArr;
	}

	public void setPackageArr(List<String> packageArr) {
		this.packageArr = packageArr;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public String getDrdsId() {
		return drdsId;
	}

	public void setDrdsId(String drdsId) {
		this.drdsId = drdsId;
	}

}
