package com.sobey.storage.webservice.response.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.storage.constans.WsConstants;

/**
 * <pre>
 * volumeName		卷名
 * clientIPs		需要写入netapp Client Permissions 里的ip
 * controllerIP		netapp控制器IP
 * username			netapp控制器登录名
 * password			netapp控制器登录密码
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "ModifytEs3RuleParameter")
@XmlType(name = "ModifytEs3RuleParameter", namespace = WsConstants.NS)
public class ModifytEs3RuleParameter {

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

	/**
	 * 需要写入netapp Client Permissions 里的ip
	 */
	private List<String> clientIPs;

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

	public List<String> getClientIPs() {
		return clientIPs;
	}

	public void setClientIPs(List<String> clientIPs) {
		this.clientIPs = clientIPs;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
