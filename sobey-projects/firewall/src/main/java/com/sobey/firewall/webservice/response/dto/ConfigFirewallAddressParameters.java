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

	public ArrayList<ConfigFirewallAddressParameter> getConfigFirewallAddressParameters() {
		return configFirewallAddressParameters;
	}

	public void setConfigFirewallAddressParameters(
			ArrayList<ConfigFirewallAddressParameter> configFirewallAddressParameters) {
		this.configFirewallAddressParameters = configFirewallAddressParameters;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
