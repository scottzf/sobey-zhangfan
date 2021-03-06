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
 * IdcHistory generated by hbm2java
 */
@Entity
@Table(name = "idc_history", schema = "public")
public class IdcHistory extends BasicEntity {

	private String address;
	private String city;
	private Date endDate;
	private Idc idc;
	private String phone;
	private String remark;
	private String zip;

	public IdcHistory() {
	}

	@Column(name = "address", length = 200)
	public String getAddress() {
		return address;
	}

	@Column(name = "city", length = 100)
	public String getCity() {
		return city;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return endDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public Idc getIdc() {
		return idc;
	}

	@Column(name = "phone", length = 100)
	public String getPhone() {
		return phone;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	@Column(name = "zip", length = 200)
	public String getZip() {
		return zip;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setIdc(Idc idc) {
		this.idc = idc;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
