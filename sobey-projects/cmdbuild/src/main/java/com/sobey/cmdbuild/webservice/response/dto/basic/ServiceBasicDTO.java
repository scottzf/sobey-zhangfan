package com.sobey.cmdbuild.webservice.response.dto.basic;

import java.util.Date;

import com.sobey.cmdbuild.webservice.response.dto.IdcDTO;
import com.sobey.cmdbuild.webservice.response.dto.SubnetDTO;
import com.sobey.cmdbuild.webservice.response.dto.TenantsDTO;

public abstract class ServiceBasicDTO extends BasicDTO {

	protected Integer agentType;
	protected String agentTypeText;
	protected Date createTime;
	protected Integer idc;
	protected IdcDTO idcDTO;
	protected Integer subnet;
	protected SubnetDTO subnetDTO;
	protected Integer tenants;
	protected TenantsDTO tenantsDTO;

	public Integer getAgentType() {
		return agentType;
	}

	public String getAgentTypeText() {
		return agentTypeText;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Integer getIdc() {
		return idc;
	}

	public IdcDTO getIdcDTO() {
		return idcDTO;
	}

	public Integer getSubnet() {
		return subnet;
	}

	public SubnetDTO getSubnetDTO() {
		return subnetDTO;
	}

	public Integer getTenants() {
		return tenants;
	}

	public TenantsDTO getTenantsDTO() {
		return tenantsDTO;
	}

	public void setAgentType(Integer agentType) {
		this.agentType = agentType;
	}

	public void setAgentTypeText(String agentTypeText) {
		this.agentTypeText = agentTypeText;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setIdcDTO(IdcDTO idcDTO) {
		this.idcDTO = idcDTO;
	}

	public void setSubnet(Integer subnet) {
		this.subnet = subnet;
	}

	public void setSubnetDTO(SubnetDTO subnetDTO) {
		this.subnetDTO = subnetDTO;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	public void setTenantsDTO(TenantsDTO tenantsDTO) {
		this.tenantsDTO = tenantsDTO;
	}

}
