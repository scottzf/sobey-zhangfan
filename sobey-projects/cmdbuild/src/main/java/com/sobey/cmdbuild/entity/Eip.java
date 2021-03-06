package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.ServiceBasic;

/**
 * Eip generated by hbm2java
 */
@Entity
@Table(name = "eip", schema = "public")
public class Eip extends ServiceBasic {

	private Integer bandwidth;
	private Set<EipHistory> eipHistories = new HashSet<EipHistory>(0);
	private Integer eipStatus;
	private Integer ipaddress;
	private Integer isp;

	public Eip() {
	}

	@Column(name = "bandwidth")
	public Integer getBandwidth() {
		return bandwidth;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eip")
	public Set<EipHistory> getEipHistories() {
		return eipHistories;
	}

	@Column(name = "eip_status")
	public Integer getEipStatus() {
		return eipStatus;
	}

	@Column(name = "ipaddress")
	public Integer getIpaddress() {
		return ipaddress;
	}

	@Column(name = "isp")
	public Integer getIsp() {
		return isp;
	}

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}

	public void setEipHistories(Set<EipHistory> eipHistories) {
		this.eipHistories = eipHistories;
	}

	public void setEipStatus(Integer eipStatus) {
		this.eipStatus = eipStatus;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setIsp(Integer isp) {
		this.isp = isp;
	}

}
