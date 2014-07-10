package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.DeviceBasic;

/**
 * Firewall generated by hbm2java
 */
@Entity
@Table(name = "firewall", schema = "public")
public class Firewall extends DeviceBasic {

	private Set<FirewallHistory> firewallHistories = new HashSet<FirewallHistory>(0);

	public Firewall() {
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "firewall")
	public Set<FirewallHistory> getFirewallHistories() {
		return this.firewallHistories;
	}

	public void setFirewallHistories(Set<FirewallHistory> firewallHistories) {
		this.firewallHistories = firewallHistories;
	}

}
