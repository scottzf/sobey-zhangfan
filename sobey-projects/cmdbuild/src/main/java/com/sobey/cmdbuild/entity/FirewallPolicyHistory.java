package com.sobey.cmdbuild.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.BasicEntity;

/**
 * ConfigFirewallAddressHistory generated by hbm2java
 */
@Entity
@Table(name = "firewall_policy_history", schema = "public")
public class FirewallPolicyHistory extends BasicEntity {

	/**
	 * 行为 allow & deny
	 */
	private Integer action;

	/**
	 * IP地址, 下行时为源,上行时为目标
	 */
	private String address;

	private FirewallPolicy configFirewallServiceCategory;

	/**
	 * 方向 上行 & 下行
	 */
	private Integer direction;

	/**
	 * 结束端口
	 */
	private Integer endPort;

	/**
	 * 所属FirewallService
	 */
	private Integer firewallService;

	/**
	 * 协议 TCP & UDP
	 */
	private Integer protocol;

	/**
	 * 起始端口
	 */
	private Integer startPort;

	public FirewallPolicyHistory() {
	}

	@Column(name = "action")
	public Integer getAction() {
		return action;
	}

	@Column(name = "address", length = 100)
	public String getAddress() {
		return address;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public FirewallPolicy getConfigFirewallServiceCategory() {
		return configFirewallServiceCategory;
	}

	@Column(name = "direction")
	public Integer getDirection() {
		return direction;
	}

	@Column(name = "end_port")
	public Integer getEndPort() {
		return endPort;
	}

	@Column(name = "firewall_service")
	public Integer getFirewallService() {
		return firewallService;
	}

	@Column(name = "protocol")
	public Integer getProtocol() {
		return protocol;
	}

	@Column(name = "start_port")
	public Integer getStartPort() {
		return startPort;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setConfigFirewallServiceCategory(FirewallPolicy configFirewallServiceCategory) {
		this.configFirewallServiceCategory = configFirewallServiceCategory;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public void setEndPort(Integer endPort) {
		this.endPort = endPort;
	}

	public void setFirewallService(Integer firewallService) {
		this.firewallService = firewallService;
	}

	public void setProtocol(Integer protocol) {
		this.protocol = protocol;
	}

	public void setStartPort(Integer startPort) {
		this.startPort = startPort;
	}

}
