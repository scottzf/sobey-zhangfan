package com.sobey.sdn.bean;

public class Subnet {

	private String subnetId;  //子网ID
	
	private String subnetName;  //子网名称
	
	private String segment;  //网段  
	
	private String gateway;  //网关
	
	private String subnetMask;  //子网掩码
	
	private String port;  //子网端口号
	
	private String tenementId;  //租户ID
	
	private String routerName;  //所连路由器名称
	
	private String portGroupName;  //子网所跑的端口组
	
	private int portNo; //子网所连端口序号

	public String getSubnetId() {
		return subnetId;
	}

	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}

	public String getSubnetName() {
		return subnetName;
	}

	public void setSubnetName(String subnetName) {
		this.subnetName = subnetName;
	}

	public String getSubnetMask() {
		return subnetMask;
	}

	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getTenementId() {
		return tenementId;
	}

	public void setTenementId(String tenementId) {
		this.tenementId = tenementId;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

	public String getPortGroupName() {
		return portGroupName;
	}

	public void setPortGroupName(String portGroupName) {
		this.portGroupName = portGroupName;
	}

	public int getPortNo() {
		return portNo;
	}

	public void setPortNo(int portNo) {
		this.portNo = portNo;
	}

}
