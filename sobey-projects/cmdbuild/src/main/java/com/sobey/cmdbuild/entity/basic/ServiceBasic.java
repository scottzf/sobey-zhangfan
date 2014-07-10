package com.sobey.cmdbuild.entity.basic;

import javax.persistence.Column;

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
public class ServiceBasic extends BasicEntity {

	protected Integer tenants;
	protected Integer idc;
	protected Integer agentType;
	protected String remark;

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

}
