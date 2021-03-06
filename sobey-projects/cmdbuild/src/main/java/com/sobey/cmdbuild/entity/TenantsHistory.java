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
 * TenantsHistory generated by hbm2java
 */
@Entity
@Table(name = "tenants_history", schema = "public")
public class TenantsHistory extends BasicEntity {

	private String accessKey;
	private String company;
	private String email;
	private Date endDate;
	private String phone;
	private String remark;
	private Tenants tenants;

	public TenantsHistory() {
	}

	@Column(name = "access_key", length = 100)
	public String getAccessKey() {
		return accessKey;
	}

	@Column(name = "company", length = 100)
	public String getCompany() {
		return company;
	}

	@Column(name = "email", length = 100)
	public String getEmail() {
		return email;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return endDate;
	}

	@Column(name = "phone", length = 100)
	public String getPhone() {
		return phone;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public Tenants getTenants() {
		return tenants;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTenants(Tenants tenants) {
		this.tenants = tenants;
	}

}
