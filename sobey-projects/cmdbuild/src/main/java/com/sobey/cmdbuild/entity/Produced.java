package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.BasicEntity;

/**
 * produced generated by hbm2java
 */
@Entity
@Table(name = "produced", schema = "public")
public class Produced extends BasicEntity {

	private Integer ecsSpec;
	private Integer idc;
	private Integer osType;
	private Set<ProducedHistory> producedHistories = new HashSet<ProducedHistory>(0);
	private String remark;
	private Integer server;

	public Produced() {
	}

	@Column(name = "ecs_spec")
	public Integer getEcsSpec() {
		return ecsSpec;
	}

	@Column(name = "idc")
	public Integer getIdc() {
		return idc;
	}

	@Column(name = "os_type")
	public Integer getOsType() {
		return osType;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "produced")
	public Set<ProducedHistory> getProducedHistories() {
		return producedHistories;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	@Column(name = "server")
	public Integer getServer() {
		return server;
	}

	public void setEcsSpec(Integer ecsSpec) {
		this.ecsSpec = ecsSpec;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setOsType(Integer osType) {
		this.osType = osType;
	}

	public void setProducedHistories(Set<ProducedHistory> producedHistories) {
		this.producedHistories = producedHistories;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setServer(Integer server) {
		this.server = server;
	}

}
