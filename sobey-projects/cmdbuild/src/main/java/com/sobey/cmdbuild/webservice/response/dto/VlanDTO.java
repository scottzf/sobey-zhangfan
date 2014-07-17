package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "VlanDTO")
@XmlType(name = "VlanDTO", namespace = WsConstants.NS)
public class VlanDTO extends BasicDTO {

	private String gateway;
	private Integer idc;
	private IdcDTO idcDTO;
	private String netMask;
	private String segment;
	private Integer tenants;
	private TenantsDTO tenantsDTO;
	private Integer vlanStatus;
	private String vlanStatusText;

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public Integer getIdc() {
		return idc;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public IdcDTO getIdcDTO() {
		return idcDTO;
	}

	public void setIdcDTO(IdcDTO idcDTO) {
		this.idcDTO = idcDTO;
	}

	public String getNetMask() {
		return netMask;
	}

	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public Integer getTenants() {
		return tenants;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	public TenantsDTO getTenantsDTO() {
		return tenantsDTO;
	}

	public void setTenantsDTO(TenantsDTO tenantsDTO) {
		this.tenantsDTO = tenantsDTO;
	}

	public Integer getVlanStatus() {
		return vlanStatus;
	}

	public void setVlanStatus(Integer vlanStatus) {
		this.vlanStatus = vlanStatus;
	}

	public String getVlanStatusText() {
		return vlanStatusText;
	}

	public void setVlanStatusText(String vlanStatusText) {
		this.vlanStatusText = vlanStatusText;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}