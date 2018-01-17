package org.roof.spring;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 服务器端响应的消息格式
 * 
 * @author liuxin
 * 
 */
public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6048347045275292432L;
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String ERROR = "error";
	/**
	 * 错误编码
	 */
	private String exceptionCode;

	/**
	 * 状态
	 * 
	 */
	private String state;

	/**
	 * 服务器返回到页面的消息
	 */
	private String message;

	/**
	 * 服务器返回数据
	 */
	private Object data;

	public Result() {
	}

	/**
	 * 创建一个返回消息,消息状态为<code>success</code>
	 * 
	 * @param message
	 *            返回到客户端的信息
	 */
	public Result(String message) {
		this(SUCCESS, message);
	}

	/**
	 * 创建一个返回消息,状态为 {@link Result#SUCCESS}, {@link Result#ERROR},
	 * {@link Result#FAIL}
	 * 
	 * @param state
	 *            状态
	 * @param message
	 *            返回到客户端的信息
	 */
	public Result(String state, String message) {
		super();
		this.state = state;
		this.message = message;
	}

	/**
	 * 创建一个返回消息,状态为 {@link Result#SUCCESS}, {@link Result#ERROR},
	 * {@link Result#FAIL},并带有服务器端返回的业务数据
	 * 
	 * @param state
	 *            状态
	 * @param data
	 *            返回的业务数据
	 */
	public Result(String state, Object data) {
		super();
		this.state = state;
		this.data = data;
	}

	/**
	 * 创建一个返回消息,状态为 {@link Result#SUCCESS}, {@link Result#ERROR},
	 * {@link Result#FAIL},并带有服务器端返回的异常信息
	 * <p>
	 * 如果 throwable是{@link org.roof.exceptions.ApplicationException}类型,
	 * 会自动将exceptionCode属性填充
	 * 
	 * @param state
	 *            服务器状态
	 * @param throwable
	 *            异常信息
	 */
	public Result(String state, Throwable throwable) {
		super();
		this.state = state;
		this.message = throwable.getMessage();
		if (throwable instanceof ApplicationException) {
			ApplicationException applicationException = (ApplicationException) throwable;
			this.exceptionCode = applicationException.getExceptionCode();
		}
	}

	/**
	 * 创建一个返回消息,状态为 {@link Result#ERROR},并带有服务器端返回的异常信息
	 * <p>
	 * 如果 throwable是{@link org.roof.exceptions.ApplicationException}类型,
	 * 会自动将exceptionCode属性填充
	 * 
	 * @param throwable
	 *            异常信息
	 */
	public Result(Throwable throwable) {
		this(ERROR, throwable);
	}

	/**
	 * 创建一个返回消息{@link Result#SUCCESS}, {@link Result#ERROR}, {@link Result#FAIL}
	 * ,并带有服务器端返回的信息和业务数据
	 * 
	 * @param state
	 *            服务器状态
	 * @param message
	 *            返回到客户端的信息
	 * @param data
	 *            返回的业务数据
	 */
	public Result(String state, String message, Object data) {
		super();
		this.state = state;
		this.message = message;
		this.data = data;
	}

	public String getState() {
		return state;
	}

	public String getMessage() {
		return message;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	/**
	 * 将Result转化为Json字符串
	 * 
	 * @return json字符串
	 */
	public String toJson() {
		return getStr(this);
	}

	/**
	 * 将JavaBean或者集合转换为JSON字符串, 使用默认的日期格式(yyyy-MM-dd)
	 * 
	 * @param data
	 *            需要转换的JavaBean
	 * @return 产生的JSON字符串
	 */
	public static String getStr(Object data) {
		return getStr(data, DateJsonValueProcessor.DEFAULT_DATE_PATTERN);
	}

	/**
	 * 将JavaBean或者集合转换为JSON类, 使用指定的日期格式
	 * 
	 * @param data
	 *            需要转换的JavaBean
	 * @param datePattern
	 *            日期格式
	 * @return 产生的JSON字符串
	 */
	public static String getStr(Object data, String datePattern) {
		return JSON.toJSONStringWithDateFormat(data, datePattern);
	}
}
