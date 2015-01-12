package com.sobey.sdn.bean;

import java.util.ArrayList;

public class CreateEipParameter {
	
	private String vRouterIp;   //操纵路由器的管理IP
	
	private String vipGroupName;   //eip映射的全局组名
	
	/**
	 * 映射的名称为 vip +"_"+ protocol + "_" + protocolPort
	 */
	
	private String vip;  //公网IP
	
	private String protocol;  //eip访问所使用的协议
	
	private int protocolPort;  //eip访问的协议端口
	
	private String privateIP;  //内网IP
	
	private int internetPort;  //公网所连路由器的端口序号
	
	private int subnetPort;   //子网所连路由器的端口序号

	private int strategyNo;   //公网端口与子网端口配置策略时的策略号  （每条策略号不能重复）

	private ArrayList<String> allPolicies;   //所有映射成员集合

	public String getvRouterIp() {
		return vRouterIp;
	}

	public void setvRouterIp(String vRouterIp) {
		this.vRouterIp = vRouterIp;
	}

	public String getVipGroupName() {
		return vipGroupName;
	}

	public void setVipGroupName(String vipGroupName) {
		this.vipGroupName = vipGroupName;
	}

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getProtocolPort() {
		return protocolPort;
	}

	public void setProtocolPort(int protocolPort) {
		this.protocolPort = protocolPort;
	}

	public String getPrivateIP() {
		return privateIP;
	}

	public void setPrivateIP(String privateIP) {
		this.privateIP = privateIP;
	}

	public int getInternetPort() {
		return internetPort;
	}

	public void setInternetPort(int internetPort) {
		this.internetPort = internetPort;
	}

	public int getSubnetPort() {
		return subnetPort;
	}

	public void setSubnetPort(int subnetPort) {
		this.subnetPort = subnetPort;
	}

	public int getStrategyNo() {
		return strategyNo;
	}

	public void setStrategyNo(int strategyNo) {
		this.strategyNo = strategyNo;
	}

	public ArrayList<String> getAllPolicies() {
		return allPolicies;
	}

	public void setAllPolicies(ArrayList<String> allPolicies) {
		this.allPolicies = allPolicies;
	}

}
