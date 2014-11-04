package com.sobey.storage.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.storage.constans.WsConstants;

/**
 * 
 * <pre>
 * clientIP			卸载客户端IP
 * controllerIP		netapp控制器IP
 * username			netapp控制器登录名
 * password			netapp控制器登录密码
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "UmountEs3Parameter")
@XmlType(name = "UmountEs3Parameter", namespace = WsConstants.NS)
public class UmountEs3Parameter {

	/**
	 * 卸载客户端IP
	 */
	private String clientIP;

	/**
	 * netapp控制器IP
	 */
	private String controllerIP;

	/**
	 * netapp控制器登录名
	 */
	private String username;

	/**
	 * netapp控制器登录密码
	 */
	private String password;

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public String getControllerIP() {
		return controllerIP;
	}

	public void setControllerIP(String controllerIP) {
		this.controllerIP = controllerIP;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
