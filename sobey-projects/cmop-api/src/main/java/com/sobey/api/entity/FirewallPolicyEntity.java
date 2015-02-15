package com.sobey.api.entity;

public class FirewallPolicyEntity {

	/**
	 * 行为 allow & deny
	 */
	private String action;

	/**
	 * IP地址, 下行时为源,上行时为目标
	 */
	private String address;

	/**
	 * 方向 上行 & 下行
	 */
	private String direction;

	/**
	 * 结束端口
	 */
	private Integer endPort;

	/**
	 * 所属FirewallService
	 */
	private Integer firewallService;

	private String identifier;

	/**
	 * 协议 TCP & UDP
	 */
	private String protocol;

	/**
	 * 起始端口
	 */
	private Integer startPort;

	public FirewallPolicyEntity(String action, String address, String direction, Integer endPort,
			Integer firewallService, String identifier, String protocol, Integer startPort) {
		super();
		this.action = action;
		this.address = address;
		this.direction = direction;
		this.endPort = endPort;
		this.firewallService = firewallService;
		this.identifier = identifier;
		this.protocol = protocol;
		this.startPort = startPort;
	}

	public String getAction() {
		return action;
	}

	public String getAddress() {
		return address;
	}

	public String getDirection() {
		return direction;
	}

	public Integer getEndPort() {
		return endPort;
	}

	public Integer getFirewallService() {
		return firewallService;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getProtocol() {
		return protocol;
	}

	public Integer getStartPort() {
		return startPort;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setEndPort(Integer endPort) {
		this.endPort = endPort;
	}

	public void setFirewallService(Integer firewallService) {
		this.firewallService = firewallService;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setStartPort(Integer startPort) {
		this.startPort = startPort;
	}

}
