package com.sobey.loadbalancer.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.loadbalancer.constans.WsConstants;

@XmlRootElement(name = "ElbPolicySync")
@XmlType(name = "ElbPolicySync", namespace = WsConstants.NS)
public class ElbPolicySync {

	private String elb;
	private String elbProtocol;
	private String ipaddress;
	private Integer sourcePort;
	private Integer targetPort;

	public String getElb() {
		return elb;
	}

	public void setElb(String elb) {
		this.elb = elb;
	}

	public String getElbProtocol() {
		return elbProtocol;
	}

	public void setElbProtocol(String elbProtocol) {
		this.elbProtocol = elbProtocol;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
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