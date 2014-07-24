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
 * Esg generated by hbm2java
 */
@Entity
@Table(name = "esg", schema = "public")
public class Esg extends ServiceBasic {

	private Boolean isDefault;
	private Set<EsgHistory> esgHistories = new HashSet<EsgHistory>(0);

	public Esg() {
	}

	@Column(name = "is_default")
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "esg")
	public Set<EsgHistory> getEsgHistories() {
		return esgHistories;
	}

	public void setEsgHistories(Set<EsgHistory> esgHistories) {
		this.esgHistories = esgHistories;
	}

}
