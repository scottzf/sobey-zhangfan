package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "SubnetDTO")
@XmlType(name = "SubnetDTO", namespace = WsConstants.NS)
public class SubnetDTO extends BasicDTO {

	private String gateway;
	private Integer idc;
	private IdcDTO idcDTO;
	private String netMask;
	private String segment;
	private Integer tenants;
	private TenantsDTO tenantsDTO;

	public String getGateway() {
		return gateway;
	}

	public Integer getIdc() {
		return idc;
	}

	public IdcDTO getIdcDTO() {
		return idcDTO;
	}

	public String getNetMask() {
		return netMask;
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

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setIdcDTO(IdcDTO idcDTO) {
		this.idcDTO = idcDTO;
	}

	public void setNetMask(String netMask) {
		this.netMask = netMask;
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