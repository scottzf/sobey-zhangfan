package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "ElbPolicyDTO")
@XmlType(name = "ElbPolicyDTO", namespace = WsConstants.NS)
public class ElbPolicyDTO extends BasicDTO {

	private Integer elb;
	private Integer elbProtocol;
	private String elbProtocolText;
	private Integer sourcePort;
	private Integer targetPort;

	public Integer getElb() {
		return elb;
	}

	public void setElb(Integer elb) {
		this.elb = elb;
	}

	public Integer getElbProtocol() {
		return elbProtocol;
	}

	public void setElbProtocol(Integer elbProtocol) {
		this.elbProtocol = elbProtocol;
	}

	public String getElbProtocolText() {
		return elbProtocolText;
	}

	public void setElbProtocolText(String elbProtocolText) {
		this.elbProtocolText = elbProtocolText;
	}

	public Integer getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(Integer sourcePort) {
		this.sourcePort = sourcePort;
	}

	public Integer getTargetPort() {
		return targetPort;
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