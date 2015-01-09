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
	 * 防火墙(路由)登录密码
	 */
	private String password;

	/**
	 * 网段
	 */
	private String segment;

	/**
	 * 子网掩码
	 */
	private String subnetMask;

	/**
	 * 防火墙(路由)IP地址
	 */
	private String url;

	/**
	 * 防火墙(路由)登录名
	 */
	private String userName;

	public String getGateway() {
		return gateway;
	}

	public String getPassword() {
		return password;
	}

	public String getSegment() {
		return segment;
	}

	public String getSubnetMask() {
		return subnetMask;
	}

	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
