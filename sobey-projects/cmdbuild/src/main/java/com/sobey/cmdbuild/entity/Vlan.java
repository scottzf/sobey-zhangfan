package com.sobey.cmdbuild.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Vlan generated by hbm2java
 */
@Entity
@Table(name = "vlan", schema = "public")
public class Vlan extends BasicEntity {

	private String gateway;
	private Integer idc;
	private String netMask;
	private String remark;
	private String segment;
	private Set<VlanHistory> vlanHistories = new HashSet<VlanHistory>(0);

	public Vlan() {
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"BeginDate\"", nullable = false, length = 29)
	public Date getBeginDate() {
		return this.beginDate;
	}

	@Column(name = "gateway", length = 50)
	public String getGateway() {
		return this.gateway;
	}

	@Column(name = "\"IDC\"")
	public Integer getIdc() {
		return this.idc;
	}

	@Column(name = "net_mask", length = 50)
	public String getNetMask() {
		return this.netMask;
	}

	@Column(name = "\"Remark\"", length = 250)
	public String getRemark() {
		return this.remark;
	}

	@Column(name = "segment", length = 50)
	public String getSegment() {
		return this.segment;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vlan")
	public Set<VlanHistory> getVlanHistories() {
		return this.vlanHistories;
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

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public void setVlanHistories(Set<VlanHistory> vlanHistories) {
		this.vlanHistories = vlanHistories;
	}

}
