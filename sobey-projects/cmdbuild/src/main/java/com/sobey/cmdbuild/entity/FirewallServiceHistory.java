package com.sobey.cmdbuild.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.ServiceHistoryBasic;

/**
 * EcsHistory generated by hbm2java
 */
@Entity
@Table(name = "firewall_service_history", schema = "public")
public class FirewallServiceHistory extends ServiceHistoryBasic {

	private FirewallService firewallService;

	public FirewallServiceHistory() {
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public FirewallService getFirewallService() {
		return firewallService;
	}

	public void setFirewallService(FirewallService firewallService) {
		this.firewallService = firewallService;
	}

}
