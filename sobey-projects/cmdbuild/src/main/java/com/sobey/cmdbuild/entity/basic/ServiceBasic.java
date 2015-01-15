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
 * ECS ES3 ELB EIP DNS VPN Firewall Router
 * </p>
 * 
 * @author Administrator
 *
 */
@MappedSuperclass
public class ServiceBasic extends BasicEntity {

	protected Integer agentType;
	protected Date creataTime;
	protected Integer idc;
	protected String remark;
	protected Integer tenants;

	@Column(name = "agent_type")
	public Integer getAgentType() {
		return agentType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 29)
	public Date getCreataTime() {
		return creataTime;
	}

	@Column(name = "idc")
	public Integer getIdc() {
		return idc;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	@Column(name = "tenants")
	public Integer getTenants() {
		return tenants;
	}

	public void setAgentType(Integer agentType) {
		this.agentType = agentType;
	}

	public void setCreataTime(Date creataTime) {
		this.creataTime = creataTime;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

}
