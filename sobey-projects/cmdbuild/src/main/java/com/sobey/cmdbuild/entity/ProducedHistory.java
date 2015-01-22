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
 * ProducedHistory generated by hbm2java
 */
@Entity
@Table(name = "produced_history", schema = "public")
public class ProducedHistory extends BasicEntity {

	private Integer ecsSpec;
	private Date endDate;
	private Integer idc;
	private Integer osType;
	private Produced produced;
	private String remark;
	private Integer server;

	public ProducedHistory() {
	}

	@Column(name = "ecs_spec")
	public Integer getEcsSpec() {
		return ecsSpec;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return endDate;
	}

	@Column(name = "idc")
	public Integer getIdc() {
		return idc;
	}

	@Column(name = "os_type")
	public Integer getOsType() {
		return osType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public Produced getProduced() {
		return produced;
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

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setOsType(Integer osType) {
		this.osType = osType;
	}

	public void setProduced(Produced produced) {
		this.produced = produced;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setServer(Integer server) {
		this.server = server;
	}

}
