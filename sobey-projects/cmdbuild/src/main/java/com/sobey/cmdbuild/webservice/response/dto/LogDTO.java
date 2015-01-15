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
	private Integer result;
	private Integer serviceType;
	private Integer tenants;

	public Integer getOperateType() {
		return operateType;
	}

	public Integer getResult() {
		return result;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public Integer getTenants() {
		return tenants;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}