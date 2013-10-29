package com.sobey.cmdbuild.entity;

// Generated 2013-10-28 14:02:09 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Company generated by hbm2java
 */
@Entity
@Table(name = "company", schema = "public")
public class Company implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String idClass;
	private String code;
	private String description;
	private Character status;
	private String user;
	private Date beginDate;
	private String notes;
	private String zip;
	private String phone;
	private String address;
	private String remark;
	private Set<CompanyHistory> companyHistories = new HashSet<CompanyHistory>(0);

	public Company() {
	}

	public Company(Integer id, String idClass, Date beginDate) {
		this.id = id;
		this.idClass = idClass;
		this.beginDate = beginDate;
	}

	public Company(Integer id, String idClass, String code, String description, Character status, String user,
			Date beginDate, String notes, String zip, String phone, String address, String remark,
			Set<CompanyHistory> companyHistories) {
		this.id = id;
		this.idClass = idClass;
		this.code = code;
		this.description = description;
		this.status = status;
		this.user = user;
		this.beginDate = beginDate;
		this.notes = notes;
		this.zip = zip;
		this.phone = phone;
		this.address = address;
		this.remark = remark;
		this.companyHistories = companyHistories;
	}

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "\"Id\"", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "\"IdClass\"", nullable = false)
	public String getIdClass() {
		return this.idClass;
	}

	public void setIdClass(String idClass) {
		this.idClass = idClass;
	}

	@Column(name = "\"Code\"", length = 100)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "\"Description\"", length = 250)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "\"Status\"", length = 1)
	public Character getStatus() {
		return this.status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	@Column(name = "\"User\"", length = 40)
	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"BeginDate\"", nullable = false, length = 29)
	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Column(name = "\"Notes\"")
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "\"Zip\"", length = 100)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "\"Phone\"", length = 100)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "\"Address\"", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "\"Remark\"", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<CompanyHistory> getCompanyHistories() {
		return this.companyHistories;
	}

	public void setCompanyHistories(Set<CompanyHistory> companyHistories) {
		this.companyHistories = companyHistories;
	}

}
