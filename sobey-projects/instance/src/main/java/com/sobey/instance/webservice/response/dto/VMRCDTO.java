package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sobey.instance.constans.WsConstants;

@XmlRootElement(name = "VMRCDTO")
@XmlType(name = "VMRCDTO", namespace = WsConstants.NS)
public class VMRCDTO {

	private Boolean allowSSLValidationErrors; // 是否允许SSL验证错误（默认为是）

	private String hostName; // vcenter ip地址（此处命名以vmrc插件页面显身名称为准）

	private String hostSSLThumbprint; // 主机认证证书指纹

	private String password; // vcenter登陆密码

	private String userName; // vcenter登陆用户名

	private String vmId; // 虚拟机引用ID（MOR的id）

	public Boolean getAllowSSLValidationErrors() {
		return allowSSLValidationErrors;
	}

	public String getHostName() {
		return hostName;
	}

	public String getHostSSLThumbprint() {
		return hostSSLThumbprint;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public String getVmId() {
		return vmId;
	}

	public void setAllowSSLValidationErrors(Boolean allowSSLValidationErrors) {
		this.allowSSLValidationErrors = allowSSLValidationErrors;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setHostSSLThumbprint(String hostSSLThumbprint) {
		this.hostSSLThumbprint = hostSSLThumbprint;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

}
