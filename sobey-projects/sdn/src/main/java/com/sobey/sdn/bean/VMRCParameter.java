package com.sobey.sdn.bean;

public class VMRCParameter {

	private String hostSSLThumbprint;    //主机认证证书指纹
	
	private String hostName;    //vcenter ip地址（此处命名以vmrc插件页面显身名称为准）
	
	private String userName;    //vcenter登陆用户名
	
	private String password;    //vcenter登陆密码
	
	private String vmId;    //虚拟机引用ID（MOR的id）
	
	private Boolean allowSSLValidationErrors;    //是否允许SSL验证错误（默认为是）

	public String getHostSSLThumbprint() {
		return hostSSLThumbprint;
	}

	public void setHostSSLThumbprint(String hostSSLThumbprint) {
		this.hostSSLThumbprint = hostSSLThumbprint;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public Boolean getAllowSSLValidationErrors() {
		return allowSSLValidationErrors;
	}

	public void setAllowSSLValidationErrors(Boolean allowSSLValidationErrors) {
		this.allowSSLValidationErrors = allowSSLValidationErrors;
	}
	
}
