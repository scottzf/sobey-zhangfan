package com.sobey.firewall.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.firewall.constans.WsConstants;

/**
 * 防火墙上,执行脚本所需参数EIPParameter的policyParameters对象:
 * 
 * <pre>
 * protocolText  	协议
 * sourcePort	源端口
 * targetPort	目标端口
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "EIPPolicyParameter")
@XmlType(name = "EIPPolicyParameter", namespace = WsConstants.NS)
public class EIPPolicyParameter {

	/**
	 * 协议
	 */
	private String protocolText;

	/**
	 * 源端口
	 */
	private Integer sourcePort;

	/**
	 * 目标端口
	 */
	private Integer targetPort;

	public String getProtocolText() {
		return protocolText;
	}

	public Integer getSourcePort() {
		return sourcePort;
	}

	public Integer getTargetPort() {
		return targetPort;
	}

	public void setProtocolText(String protocolText) {
		this.protocolText = protocolText;
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
