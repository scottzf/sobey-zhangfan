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
 * EcsSpecHistory generated by hbm2java
 */
@Entity
@Table(name = "ecs_spec_history", schema = "public")
public class EcsSpecHistory extends BasicEntity {

	private Integer cpuNumber;

	private Integer diskSize;
	private EcsSpec ecsSpec;
	private Date endDate;
	private Integer memory;
	private Double price;
	private String remark;

	public EcsSpecHistory() {
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"BeginDate\"", nullable = false, length = 29)
	public Date getBeginDate() {
		return this.beginDate;
	}

	@Column(name = "cpu_number")
	public Integer getCpuNumber() {
		return this.cpuNumber;
	}

	@Column(name = "disk_size")
	public Integer getDiskSize() {
		return this.diskSize;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public EcsSpec getEcsSpec() {
		return this.ecsSpec;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return this.endDate;
	}

	@Column(name = "\"Memory\"")
	public Integer getMemory() {
		return this.memory;
	}

	@Column(name = "\"Price\"", precision = 17, scale = 17)
	public Double getPrice() {
		return this.price;
	}

	@Column(name = "\"Remark\"", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setCpuNumber(Integer cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public void setDiskSize(Integer diskSize) {
		this.diskSize = diskSize;
	}

	public void setEcsSpec(EcsSpec ecsSpec) {
		this.ecsSpec = ecsSpec;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
