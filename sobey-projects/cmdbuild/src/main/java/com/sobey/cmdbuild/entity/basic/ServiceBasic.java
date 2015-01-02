package com.sobey.cmdbuild.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Service 模块的基本Entity
 * 
 * <p>
 * ECS ES3 ELB EIP DNS ESG VPN
 * </p>
 * 
 * @author Administrator
 *
 */
@MappedSuperclass
public class ServiceBasic extends BasicEntity {

	protected Integer tenants;
	protected Integer idc;
	protected Integer agentType;
	protected String remark;
	protected Date creataTime;

	@Column(name = "tenants")
	public Integer getTenants() {
		return tenants;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	@Column(name = "idc")
	public Integer getIdc() {
		return idc;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	@Column(name = "agent_type")
	public Integer getAgentType() {
		return agentType;
	}

	public void setAgentType(Integer agentType) {
		this.agentType = agentType;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 29)
	public Date getCreataTime() {
		return creataTime;
	}

	public void setCreataTime(Date creataTime) {
		this.creataTime = creataTime;
	}

}
