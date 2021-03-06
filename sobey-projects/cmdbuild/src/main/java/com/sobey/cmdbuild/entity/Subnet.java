package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.BasicEntity;

/**
 * Ipaddress generated by hbm2java
 */
@Entity
@Table(name = "subnet", schema = "public")
public class Subnet extends BasicEntity {

	private Integer defaultSubnet;
	private String gateway;
	private Integer idc;
	private String netMask;
	private Integer portIndex;
	private String remark;
	private Integer router;
	private String segment;
	private Set<SubnetHistory> subnetHistories = new HashSet<SubnetHistory>(0);
	private Integer tenants;
	private Integer tunnelId;

	public Subnet() {
	}

	@Column(name = "default_subnet")
	public Integer getDefaultSubnet() {
		return defaultSubnet;
	}

	@Column(name = "gateway", length = 100)
	public String getGateway() {
		return gateway;
	}

	@Column(name = "idc")
	public Integer getIdc() {
		return idc;
	}

	@Column(name = "net_mask", length = 100)
	public String getNetMask() {
		return netMask;
	}

	@Column(name = "port_index")
	public Integer getPortIndex() {
		return portIndex;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	@Column(name = "router")
	public Integer getRouter() {
		return router;
	}

	@Column(name = "segment", length = 100)
	public String getSegment() {
		return segment;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subnet")
	public Set<SubnetHistory> getSubnetHistories() {
		return subnetHistories;
	}

	@Column(name = "tenants")
	public Integer getTenants() {
		return tenants;
	}

	@Column(name = "tunnel_id")
	public Integer getTunnelId() {
		return tunnelId;
	}

	public void setDefaultSubnet(Integer defaultSubnet) {
		this.defaultSubnet = defaultSubnet;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}

	public void setPortIndex(Integer portIndex) {
		this.portIndex = portIndex;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRouter(Integer router) {
		this.router = router;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public void setSubnetHistories(Set<SubnetHistory> subnetHistories) {
		this.subnetHistories = subnetHistories;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	public void setTunnelId(Integer tunnelId) {
		this.tunnelId = tunnelId;
	}

}
