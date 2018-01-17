package org.roof.web.resource.entity;

import java.util.Set;

/**
 * 资源(权限)
 * 
 * @author liuxin
 * 
 */
public class Privilege extends Module {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1224540985042249696L;
	/**
	 * 返回类型-json
	 */
	public static final String FORMAT_JSON = "json";
	/**
	 * 返回类型-html
	 */
	public static final String FORMAT_HTML = "html";
	/**
	 * 返回类型-xml
	 */
	public static final String FORMAT_XML = "xml";

	protected String returnExample;// 返回实例
	protected String remark;// 备注
	protected String format; // 支持格式

	protected Set<Parameter> parameters;// 请求参数
	protected Set<Parameter> returnFields;// 返回参数

	public String getReturnExample() {
		return returnExample;
	}

	public String getRemark() {
		return remark;
	}

	public Set<Parameter> getParameters() {
		return parameters;
	}

	public Set<Parameter> getReturnFields() {
		return returnFields;
	}

	public void setReturnExample(String returnExample) {
		this.returnExample = returnExample;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setParameters(Set<Parameter> parameters) {
		this.parameters = parameters;
	}

	public void setReturnFields(Set<Parameter> returnFields) {
		this.returnFields = returnFields;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
