package com.sobey.cmdbuild.webservice.response.result;

import javax.xml.bind.annotation.XmlType;

import com.sobey.cmdbuild.constants.WsConstants;

/**
 * WebService返回结果基类,定义所有返回码.
 * 
 * @author calvin
 */
@XmlType(name = "WSResult", namespace = WsConstants.NS)
public class WSResult {

	public static final String PARAMETER_ERROR = "400";
	// -- 返回代码定义 --//
	// 按项目的规则进行定义, 比如4xx代表客户端参数错误，5xx代表服务端业务错误等.
	public static final String SUCESS = "0";

	public static final String SYSTEM_ERROR = "500";
	public static final String SYSTEM_ERROR_MESSAGE = "Runtime unknown internal error.";

	// -- WSResult基本属性 --//
	private String code = SUCESS;
	private String message;

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 创建默认异常结果.
	 */
	public void setDefaultError() {
		setError(SYSTEM_ERROR, SYSTEM_ERROR_MESSAGE);
	}

	/**
	 * 创建结果.
	 */
	public void setError(String resultCode, String resultMessage) {
		code = resultCode;
		message = resultMessage;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
