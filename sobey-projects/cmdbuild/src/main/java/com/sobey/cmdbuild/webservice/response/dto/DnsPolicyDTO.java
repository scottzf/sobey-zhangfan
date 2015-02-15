package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "DnsPolicyDTO")
@XmlType(name = "DnsPolicyDTO", namespace = WsConstants.NS)
public class DnsPolicyDTO extends BasicDTO {

	private Integer dns;
	private Integer dnsProtocol;
	private String ipaddress;
	private String port;

	public Integer getDns() {
		return dns;
	}

	public Integer getDnsProtocol() {
		return dnsProtocol;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public String getPort() {
		return port;
	}

	public void setDns(Integer dns) {
		this.dns = dns;
	}

	public void setDnsProtocol(Integer dnsProtocol) {
		this.dnsProtocol = dnsProtocol;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}