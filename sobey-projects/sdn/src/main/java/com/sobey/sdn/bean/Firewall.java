package com.sobey.sdn.bean;

public class Firewall {

	private String routerName;   //vRouter名称
	
	private String firewallName;   //防火墙名称
	
	private String description;   //描述
	
	private String sourceIp;   //源IP
	
	private String action;   //行为
	
	private String sourcePort;   //源端口
	
	private String ruleType;   //规则的类型（出规则，进规则）
	
	private String controlIp;   //操纵路由器的管理IP
	
	private String eIp;   //外网IP
	
	private String eSubnetMask;   //外网掩码
	
	private String gateway;   //网关

	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

	public String getFirewallName() {
		return firewallName;
	}

	public void setFirewallName(String firewallName) {
		this.firewallName = firewallName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(String sourcePort) {
		this.sourcePort = sourcePort;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getControlIp() {
		return controlIp;
	}

	public void setControlIp(String controlIp) {
		this.controlIp = controlIp;
	}

	public String geteIp() {
		return eIp;
	}

	public void seteIp(String eIp) {
		this.eIp = eIp;
	}

	public String geteSubnetMask() {
		return eSubnetMask;
	}

	public void seteSubnetMask(String eSubnetMask) {
		this.eSubnetMask = eSubnetMask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	
 }
