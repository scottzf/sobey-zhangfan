package com.sobey.sdn.test.testParameter;

public class CreateRouterParameter {

	private String routerName; // vRouter名称

	private String hostIp; // 主机IP
	
	private String controlIp;   //操纵路由器的管理IP

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

	public String getControlIp() {
		return controlIp;
	}

	public void setControlIp(String controlIp) {
		this.controlIp = controlIp;
	}
	
	
}
