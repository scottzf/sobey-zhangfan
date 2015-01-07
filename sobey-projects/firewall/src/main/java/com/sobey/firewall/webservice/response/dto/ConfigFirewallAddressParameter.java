package com.sobey.firewall.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.firewall.constans.WsConstants;

@XmlRootElement(name = "ConfigFirewallAddressParameter")
@XmlType(name = "ConfigFirewallAddressParameter", namespace = WsConstants.NS)
public class ConfigFirewallAddressParameter {

	/**
	 * 网关
	 */
	private String gateway;

	/**
	 * 网段
	 */
	private String segment;

	/**
	 * 子网掩码
	 */
	private String subnetMask;

	public String getGateway() {
		return gateway;
	}

	public String getSegment() {
		return segment;
	}

	public String getSubnetMask() {
		return subnetMask;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
