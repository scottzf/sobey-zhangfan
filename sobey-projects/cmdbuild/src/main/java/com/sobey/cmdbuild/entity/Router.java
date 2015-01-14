package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.ServiceBasic;

@Entity
@Table(name = "router", schema = "public")
public class Router extends ServiceBasic {

	private Integer ecs;
	private Integer firewallService;
	private Integer ipaddress;
	private Set<RouterHistory> routerHistories = new HashSet<RouterHistory>(0);

	public Router() {
	}

	@Column(name = "ecs")
	public Integer getEcs() {
		return ecs;
	}

	@Column(name = "firewall_service")
	public Integer getFirewallService() {
		return firewallService;
	}

	@Column(name = "ipaddress")
	public Integer getIpaddress() {
		return ipaddress;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "router")
	public Set<RouterHistory> getRouterHistories() {
		return routerHistories;
	}

	public void setEcs(Integer ecs) {
		this.ecs = ecs;
	}

	public void setFirewallService(Integer firewallService) {
		this.firewallService = firewallService;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setRouterHistories(Set<RouterHistory> routerHistories) {
		this.routerHistories = routerHistories;
	}

}
