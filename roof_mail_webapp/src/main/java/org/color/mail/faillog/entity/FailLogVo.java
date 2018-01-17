package org.color.mail.faillog.entity;

import java.util.List;

/**
 * @author 模版生成 <br/>
 *         表名： t_fail_log <br/>
 *         描述：t_fail_log <br/>
 */
public class FailLogVo extends FailLog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7710444556187272823L;
	private List<FailLogVo> failLogList;

	public FailLogVo() {
		super();
	}

	public FailLogVo(Long id) {
		super();
		this.id = id;
	}

	public List<FailLogVo> getFailLogList() {
		return failLogList;
	}

	public void setFailLogList(List<FailLogVo> failLogList) {
		this.failLogList = failLogList;
	}

}
