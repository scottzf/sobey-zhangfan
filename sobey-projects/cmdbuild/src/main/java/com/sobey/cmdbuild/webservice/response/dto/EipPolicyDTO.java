package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "EipPolicyDTO")
@XmlType(name = "EipPolicyDTO", namespace = WsConstants.NS)
public class EipPolicyDTO extends BasicDTO {

	private Integer eip;
	private Integer eipProtocol;
	private Integer sourcePort;
	private Integer targetPort;

	public Integer getEip() {
		return eip;
	}

	public Integer getEipProtocol() {
		return eipProtocol;
	}

	public Integer getSourcePort() {
		return sourcePort;
	}

	public Integer getTargetPort() {
		return targetPort;
	}

	public void setEip(Integer eip) {
		this.eip = eip;
	}

	public void setEipProtocol(Integer eipProtocol) {
		this.eipProtocol = eipProtocol;
	}

	public void setSourcePort(Integer sourcePort) {
		this.sourcePort = sourcePort;
	}

	public void setTargetPort(Integer targetPort) {
		this.targetPort = targetPort;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}