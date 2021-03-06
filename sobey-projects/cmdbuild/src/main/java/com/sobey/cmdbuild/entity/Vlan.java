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
 * Vlan generated by hbm2java
 */
@Entity
@Table(name = "vlan", schema = "public")
public class Vlan extends BasicEntity {

	private String gateway;
	private Integer idc;
	private String netMask;
	private Integer nic;
	private String remark;
	private String segment;
	private Integer subnet;
	private Integer tenants;
	private Set<VlanHistory> vlanHistories = new HashSet<VlanHistory>(0);
	private Integer vlanId;

	public Vlan() {
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

	@Column(name = "nic")
	public Integer getNic() {
		return nic;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	@Column(name = "segment", length = 100)
	public String getSegment() {
		return segment;
	}

	@Column(name = "subnet")
	public Integer getSubnet() {
		return subnet;
	}

	@Column(name = "tenants")
	public Integer getTenants() {
		return tenants;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vlan")
	public Set<VlanHistory> getVlanHistories() {
		return vlanHistories;
	}

	@Column(name = "vlan_id")
	public Integer getVlanId() {
		return vlanId;
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

	public void setNic(Integer nic) {
		this.nic = nic;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public void setSubnet(Integer subnet) {
		this.subnet = subnet;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	public void setVlanHistories(Set<VlanHistory> vlanHistories) {
		this.vlanHistories = vlanHistories;
	}

	public void setVlanId(Integer vlanId) {
		this.vlanId = vlanId;
	}

}
