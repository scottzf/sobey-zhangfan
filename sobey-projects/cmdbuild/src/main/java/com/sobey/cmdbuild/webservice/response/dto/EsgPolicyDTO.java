package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "EsgPolicyDTO")
@XmlType(name = "EsgPolicyDTO", namespace = WsConstants.NS)
public class EsgPolicyDTO extends BasicDTO {

	private Integer esg;
	private Integer policyType;
	private String policyTypeText;
	private Integer port;
	private String sourceIP;
	private String targetIP;

	public Integer getEsg() {
		return esg;
	}

	public void setEsg(Integer esg) {
		this.esg = esg;
	}

	public Integer getPolicyType() {
		return policyType;
	}

	public void setPolicyType(Integer policyType) {
		this.policyType = policyType;
	}

	public String getPolicyTypeText() {
		return policyTypeText;
	}

	public void setPolicyTypeText(String policyTypeText) {
		this.policyTypeText = policyTypeText;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getSourceIP() {
		return sourceIP;
	}

	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	public String getTargetIP() {
		return targetIP;
	}

	public void setTargetIP(String targetIP) {
		this.targetIP = targetIP;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}