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

	private String cpuNumber;
	private String diskSize;
	private Integer firewallService;
	private Integer ipaddress;
	private String memorySize;
	private Set<RouterHistory> routerHistories = new HashSet<RouterHistory>(0);

	private Integer server;

	public Router() {
	}

	@Column(name = "cpu_number", length = 100)
	public String getCpuNumber() {
		return cpuNumber;
	}

	@Column(name = "disk_size", length = 100)
	public String getDiskSize() {
		return diskSize;
	}

	@Column(name = "firewall_service")
	public Integer getFirewallService() {
		return firewallService;
	}

	@Column(name = "ipaddress")
	public Integer getIpaddress() {
		return ipaddress;
	}

	@Column(name = "memory_size", length = 100)
	public String getMemorySize() {
		return memorySize;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "router")
	public Set<RouterHistory> getRouterHistories() {
		return routerHistories;
	}

	@Column(name = "server")
	public Integer getServer() {
		return server;
	}

	public void setCpuNumber(String cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}

	public void setFirewallService(Integer firewallService) {
		this.firewallService = firewallService;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setMemorySize(String memorySize) {
		this.memorySize = memorySize;
	}

	public void setRouterHistories(Set<RouterHistory> routerHistories) {
		this.routerHistories = routerHistories;
	}

	public void setServer(Integer server) {
		this.server = server;
	}

}
