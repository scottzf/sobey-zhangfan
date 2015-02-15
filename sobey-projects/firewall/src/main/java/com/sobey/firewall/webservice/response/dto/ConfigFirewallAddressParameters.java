package com.sobey.firewall.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.firewall.constans.WsConstants;

@XmlRootElement(name = "ConfigFirewallAddressParameters")
@XmlType(name = "ConfigFirewallAddressParameters", namespace = WsConstants.NS)
public class ConfigFirewallAddressParameters {

	private ArrayList<ConfigFirewallAddressParameter> configFirewallAddressParameters;

	/**
	 * 防火墙(路由)登录密码
	 */
	private String password;

	/**
	 * 防火墙(路由)IP地址
	 */
	private String url;

	/**
	 * 防火墙(路由)登录名
	 */
	private String userName;

	public ArrayList<ConfigFirewallAddressParameter> getConfigFirewallAddressParameters() {
		return configFirewallAddressParameters;
	}

	public String getPassword() {
		return password;
	}

	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public void setConfigFirewallAddressParameters(
			ArrayList<ConfigFirewallAddressParameter> configFirewallAddressParameters) {
		this.configFirewallAddressParameters = configFirewallAddressParameters;
	}

	public void setPassword(String password) {
		this.password = password;
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
