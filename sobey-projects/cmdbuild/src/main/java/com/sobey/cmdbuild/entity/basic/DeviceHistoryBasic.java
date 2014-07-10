package com.sobey.cmdbuild.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DeviceHistory 模块的基本Entity
 * 
 * <p>
 * Switch Server LoadBalancer Firewall Storage
 * </p>
 * 
 * @author Administrator
 *
 */
public class DeviceHistoryBasic extends BasicEntity {

	protected Integer idc;
	protected Integer rack;
	protected Integer ipaddress;
	protected Integer deviceSpec;
	protected String site;
	protected String sn;
	protected String gdzcSn;
	protected String remark;
	protected Date endDate;

	@Column(name = "idc")
	public Integer getIdc() {
		return idc;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	@Column(name = "rack")
	public Integer getRack() {
		return rack;
	}

	public void setRack(Integer rack) {
		this.rack = rack;
	}

	@Column(name = "ipaddress")
	public Integer getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	@Column(name = "device_spec")
	public Integer getDeviceSpec() {
		return deviceSpec;
	}

	public void setDeviceSpec(Integer deviceSpec) {
		this.deviceSpec = deviceSpec;
	}

	@Column(name = "site", length = 100)
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	@Column(name = "sn", length = 100)
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Column(name = "gdzcSn", length = 100)
	public String getGdzcSn() {
		return gdzcSn;
	}

	public void setGdzcSn(String gdzcSn) {
		this.gdzcSn = gdzcSn;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
