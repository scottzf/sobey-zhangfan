package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Tag generated by hbm2java
 */
@Entity
@Table(name = "tag", schema = "public")
public class Tag extends BasicEntity {

	private String remark;
	private Set<TagHistory> tagHistories = new HashSet<TagHistory>(0);
	private Integer tenants;

	public Tag() {
	}

	@Column(name = "\"Remark\"", length = 250)
	public String getRemark() {
		return this.remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tag")
	public Set<TagHistory> getTagHistories() {
		return this.tagHistories;
	}

	@Column(name = "tenants")
	public Integer getTenants() {
		return this.tenants;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTagHistories(Set<TagHistory> tagHistories) {
		this.tagHistories = tagHistories;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

}
