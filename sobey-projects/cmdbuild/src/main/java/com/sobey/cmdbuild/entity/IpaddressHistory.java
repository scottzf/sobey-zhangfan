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
 * IpaddressHistory generated by hbm2java
 */
@Entity
@Table(name = "ipaddress_history", schema = "public")
public class IpaddressHistory extends BasicEntity {

	private Ipaddress ipaddress;
	private Date endDate;
	private Integer vlan;
	private Integer isp;
	private Integer ipaddressPool;
	private Integer ipaddressStatus;
	private String netMask;
	private String gateway;

	public IpaddressHistory() {
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public Ipaddress getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(Ipaddress ipaddress) {
		this.ipaddress = ipaddress;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "vlan")
	public Integer getVlan() {
		return vlan;
	}

	public void setVlan(Integer vlan) {
		this.vlan = vlan;
	}

	@Column(name = "isp")
	public Integer getIsp() {
		return isp;
	}

	public void setIsp(Integer isp) {
		this.isp = isp;
	}

	@Column(name = "ipaddress_pool")
	public Integer getIpaddressPool() {
		return ipaddressPool;
	}

	public void setIpaddressPool(Integer ipaddressPool) {
		this.ipaddressPool = ipaddressPool;
	}

	@Column(name = "ipaddress_status")
	public Integer getIpaddressStatus() {
		return ipaddressStatus;
	}

	public void setIpaddressStatus(Integer ipaddressStatus) {
		this.ipaddressStatus = ipaddressStatus;
	}

	@Column(name = "net_mask", length = 100)
	public String getNetMask() {
		return netMask;
	}

	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}

	@Column(name = "gateway", length = 100)
	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

}
