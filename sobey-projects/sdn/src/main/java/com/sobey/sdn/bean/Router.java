package com.sobey.sdn.bean;

public class Router {

	private String routerName;   //vRouter名称
	
	private String hostIp;   //主机IP
	
	private String tenantId;   //租户ID
	
	private String localIp;   //内网IP
	
	private String eIp;   //公网IP（***** 目前设计为具有路由功能的防火墙  所以添加eIp）

	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getLocalIp() {
		return localIp;
	}

	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	public String geteIp() {
		return eIp;
	}

	public void seteIp(String eIp) {
		this.eIp = eIp;
	}
	
}
