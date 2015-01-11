package com.sobey.sdn.test.testParameter;

public class BindingFirewallParameter {

	private String routerName;   //防火墙名称
	
	private String controlIp;   //防火墙管理地址
	
	private String ip_CTC;   //电信IP
	
	private String subnetMask_CTC;   //电信掩码
	
	private String gateway_CTC;   //电信网关
	
	private int routeNo;   //路由序号
	
	private int strategyNo;   //策略序号
	
	private String subnetPort;   //子网所连端口
	
	private String subnetAddressPoolName;   //子网地址池名称

	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

	public String getControlIp() {
		return controlIp;
	}

	public void setControlIp(String controlIp) {
		this.controlIp = controlIp;
	}

	public String getIp_CTC() {
		return ip_CTC;
	}

	public void setIp_CTC(String ip_CTC) {
		this.ip_CTC = ip_CTC;
	}

	public String getSubnetMask_CTC() {
		return subnetMask_CTC;
	}

	public void setSubnetMask_CTC(String subnetMask_CTC) {
		this.subnetMask_CTC = subnetMask_CTC;
	}

	public String getGateway_CTC() {
		return gateway_CTC;
	}

	public void setGateway_CTC(String gateway_CTC) {
		this.gateway_CTC = gateway_CTC;
	}

	public int getRouteNo() {
		return routeNo;
	}

	public void setRouteNo(int routeNo) {
		this.routeNo = routeNo;
	}

	public int getStrategyNo() {
		return strategyNo;
	}

	public void setStrategyNo(int strategyNo) {
		this.strategyNo = strategyNo;
	}

	public String getSubnetPort() {
		return subnetPort;
	}

	public void setSubnetPort(String subnetPort) {
		this.subnetPort = subnetPort;
	}

	public String getSubnetAddressPoolName() {
		return subnetAddressPoolName;
	}

	public void setSubnetAddressPoolName(String subnetAddressPoolName) {
		this.subnetAddressPoolName = subnetAddressPoolName;
	}
	
}
