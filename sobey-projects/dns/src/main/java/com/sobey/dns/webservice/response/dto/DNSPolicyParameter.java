package com.sobey.dns.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.dns.constans.WsConstants;

/**
 * DNS上,执行脚本所需参数DNSPublicIPParameter的policyParameters对象:
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
@XmlRootElement(name = "DNSPolicyParameter")
@XmlType(name = "DNSPolicyParameter", namespace = WsConstants.NS)
public class DNSPolicyParameter {

	/**
	 * 端口
	 */
	private Integer port;

	/**
	 * 协议
	 */
	private String protocolText;

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getProtocolText() {
		return protocolText;
	}

	public void setProtocolText(String protocolText) {
		this.protocolText = protocolText;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
