package com.sobey.cmdbuild.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ComponentHistory 模块的基本Entity
 * 
 * <p>
 * HardDisk Nic Memory StorageBox
 * </p>
 * 
 * @author Administrator
 *
 */
@MappedSuperclass
public class ComponentHistoryBasic extends BasicEntity {

	protected Integer idc;
	protected Integer rack;
	protected Integer brand;
	protected Integer storage;
	protected Integer server;
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

	@Column(name = "brand")
	public Integer getBrand() {
		return brand;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
	}

	@Column(name = "storage")
	public Integer getStorage() {
		return storage;
	}

	public void setStorage(Integer storage) {
		this.storage = storage;
	}

	@Column(name = "server")
	public Integer getServer() {
		return server;
	}

	public void setServer(Integer server) {
		this.server = server;
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

	@Column(name = "gdzcsn", length = 100)
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
