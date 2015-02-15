package com.sobey.cmdbuild.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sobey.cmdbuild.entity.basic.BasicEntity;

/**
 * VlanHistory generated by hbm2java
 */
@Entity
@Table(name = "vlan_history", schema = "public")
public class VlanHistory extends BasicEntity {

	private Date endDate;
	private String gateway;
	private Integer idc;
	private String netMask;
	private Integer nic;
	private String remark;
	private String segment;
	private Integer subnet;
	private Integer tenants;
	private Vlan vlan;
	private Integer vlanId;

	public VlanHistory() {
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return endDate;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public Vlan getVlan() {
		return vlan;
	}

	@Column(name = "vlan_id")
	public Integer getVlanId() {
		return vlanId;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public void setVlan(Vlan vlan) {
		this.vlan = vlan;
	}

	public void setVlanId(Integer vlanId) {
		this.vlanId = vlanId;
	}

}
