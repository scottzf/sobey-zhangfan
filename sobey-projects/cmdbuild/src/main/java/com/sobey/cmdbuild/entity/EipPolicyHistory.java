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
 * EipPolicyHistory generated by hbm2java
 */
@Entity
@Table(name = "eip_olicy_history", schema = "public")
public class EipPolicyHistory extends BasicEntity {

	private Integer eip;
	private EipPolicy eipPolicy;
	private Integer eipProtocol;
	private Date endDate;
	private Integer sourcePort;
	private Integer targetPort;

	public EipPolicyHistory() {
	}

	@Column(name = "eip")
	public Integer getEip() {
		return eip;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public EipPolicy getEipPolicy() {
		return eipPolicy;
	}

	@Column(name = "eip_protocol")
	public Integer getEipProtocol() {
		return eipProtocol;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return endDate;
	}

	@Column(name = "source_port")
	public Integer getSourcePort() {
		return sourcePort;
	}

	@Column(name = "target_port")
	public Integer getTargetPort() {
		return targetPort;
	}

	public void setEip(Integer eip) {
		this.eip = eip;
	}

	public void setEipPolicy(EipPolicy eipPolicy) {
		this.eipPolicy = eipPolicy;
	}

	public void setEipProtocol(Integer eipProtocol) {
		this.eipProtocol = eipProtocol;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setSourcePort(Integer sourcePort) {
		this.sourcePort = sourcePort;
	}

	public void setTargetPort(Integer targetPort) {
		this.targetPort = targetPort;
	}

}
