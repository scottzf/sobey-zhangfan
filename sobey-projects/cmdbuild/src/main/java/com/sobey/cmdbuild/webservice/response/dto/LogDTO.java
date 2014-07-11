package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "LogDTO")
@XmlType(name = "LogDTO", namespace = WsConstants.NS)
public class LogDTO extends BasicDTO {

	private Integer operateType;
	private String operateTypeText;
	private Integer result;
	private String resultText;
	private Integer serviceType;
	private String serviceTypeText;
	private Integer tenants;
	private TenantsDTO tenantsDTO;

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public String getOperateTypeText() {
		return operateTypeText;
	}

	public void setOperateTypeText(String operateTypeText) {
		this.operateTypeText = operateTypeText;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getResultText() {
		return resultText;
	}

	public void setResultText(String resultText) {
		this.resultText = resultText;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceTypeText() {
		return serviceTypeText;
	}

	public void setServiceTypeText(String serviceTypeText) {
		this.serviceTypeText = serviceTypeText;
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

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}