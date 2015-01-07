package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "ConfigRouterStaticDTO")
@XmlType(name = "ConfigRouterStaticDTO", namespace = WsConstants.NS)
public class ConfigRouterStaticDTO extends BasicDTO {

	/**
	 * 所属公网IP
	 */
	private Integer eip;

	private EipDTO eipDTO;
	/**
	 * 所属ISP
	 */
	private Integer isp;

	private String ispText;
	/**
	 * 防火墙策略ID,唯一递增
	 */
	private Integer policyId;
	/**
	 * 防火墙路由ID,唯一递增
	 */
	private Integer routerId;
	/**
	 * 网段
	 */
	private String segment;

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

	public Integer getIsp() {
		return isp;
	}

	public String getIspText() {
		return ispText;
	}

	public Integer getPolicyId() {
		return policyId;
	}

	public Integer getRouterId() {
		return routerId;
	}

	public String getSegment() {
		return segment;
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

	public void setIsp(Integer isp) {
		this.isp = isp;
	}

	public void setIspText(String ispText) {
		this.ispText = ispText;
	}

	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}

	public void setRouterId(Integer routerId) {
		this.routerId = routerId;
	}

	public void setSegment(String segment) {
		this.segment = segment;
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