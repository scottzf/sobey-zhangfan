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

/**
 * GroupPolicyHistory generated by hbm2java
 */
@Entity
@Table(name = "group_policy_history", schema = "public")
public class GroupPolicyHistory extends BasicEntity {

	private Date endDate;
	private GroupPolicy groupPolicy;
	private String remark;
	private Integer tenants;

	public GroupPolicyHistory() {
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return this.endDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public GroupPolicy getGroupPolicy() {
		return this.groupPolicy;
	}

	@Column(name = "\"Remark\"", length = 250)
	public String getRemark() {
		return this.remark;
	}

	@Column(name = "tenants")
	public Integer getTenants() {
		return this.tenants;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setGroupPolicy(GroupPolicy groupPolicy) {
		this.groupPolicy = groupPolicy;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

}
