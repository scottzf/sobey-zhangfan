package com.sobey.dns.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.dns.constans.WsConstants;

@XmlRootElement(name = "DnsPolicySync")
@XmlType(name = "DnsPolicySync", namespace = WsConstants.NS)
public class DnsPolicySync {

	private String dns;
	private String dnsProtocol;
	private String ipaddress;
	private String port;

	public String getDns() {
		return dns;
	}

	public void setDns(String dns) {
		this.dns = dns;
	}

	public String getDnsProtocol() {
		return dnsProtocol;
	}

	public void setDnsProtocol(String dnsProtocol) {
		this.dnsProtocol = dnsProtocol;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getPort() {
		return port;
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