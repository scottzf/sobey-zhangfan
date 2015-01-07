package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "ConfigSystemInterfaceDTO")
@XmlType(name = "ConfigSystemInterfaceDTO", namespace = WsConstants.NS)
public class ConfigSystemInterfaceDTO extends BasicDTO {

	/**
	 * 所属EIP
	 */
	private Integer eip;

	private EipDTO eipDTO;

	/**
	 * 防火墙策略ID,唯一递增
	 */
	private Integer policyId;

	/**
	 * PortId
	 * 
	 * 接口名的组成格式为 "port"+"id" ,其中id 为 1-10 中一个.
	 */
	private Integer portId;

	/**
	 * 所属Tenants
	 */
	private Integer tenants;

	private TenantsDTO tenantsDTO;

	public Integer getEip() {
		return eip;
	}

	public EipDTO getEipDTO() {
		return eipDTO;
	}

	public Integer getPolicyId() {
		return policyId;
	}

	public Integer getPortId() {
		return portId;
	}

	public Integer getTenants() {
		return tenants;
	}

	public TenantsDTO getTenantsDTO() {
		return tenantsDTO;
	}

	public void setEip(Integer eip) {
		this.eip = eip;
	}

	public void setEipDTO(EipDTO eipDTO) {
		this.eipDTO = eipDTO;
	}

	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}

	public void setPortId(Integer portId) {
		this.portId = portId;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	public void setTenantsDTO(TenantsDTO tenantsDTO) {
		this.tenantsDTO = tenantsDTO;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}