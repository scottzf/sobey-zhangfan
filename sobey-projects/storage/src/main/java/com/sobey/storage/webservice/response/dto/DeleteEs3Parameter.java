package com.sobey.storage.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.storage.constans.WsConstants;

/**
 * <pre>
 * volumeName		卷名
 * controllerIP		netapp控制器IP
 * username			netapp控制器登录名
 * password			netapp控制器登录密码
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "DeleteEs3Parameter")
@XmlType(name = "DeleteEs3Parameter", namespace = WsConstants.NS)
public class DeleteEs3Parameter {

	/**
	 * 卷名
	 */
	private String volumeName;

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

	public String getVolumeName() {
		return volumeName;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
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
