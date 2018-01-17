package org.roof.commons;

public class SysConstants {
	public static final String DATA_FORMAT = "json";

	public static final String FLAG_INSERT = "insert";

	public static final String FLAG_UPDATE = "update";

	public static final String FLAG_DELETE = "delete";

	public static final String FLAG_SELECT = "select";

	public static final String AJAX_REPLY = "reply";

	public static final String AJAX_SUCCESS = "success";

	public static final String AJAX_ERROR = "error";

	public static final String AJAX_UN_LOGIN = "login";// 未登录

	public static final String AJAX_FAIL = "fail";// 失败

	public static final String SESSION_ID = "loginUser";

	public static final String SYS_PROPERTIES_NAME = "project.properties";

	public static final String DEFAULT_PWD = "123456abc";
	
	//是否自动登录
	public static final String AUTO_LOGIN = "autoLogin";
	
	public static int getAllowableErrorCount() {// 允许失败的次数
		String count = PropertiesUtil.getPorpertyString("core.allowable_error_count");
		if ("".equals(count)) {
			count = "3";
		}
		return Integer.parseInt(count);
	}
}
