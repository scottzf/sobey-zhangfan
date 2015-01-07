package com.sobey.firewall.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.firewall.constans.WsConstants;

@XmlRootElement(name = "ConfigFirewallPolicyParameters")
@XmlType(name = "ConfigFirewallPolicyParameters", namespace = WsConstants.NS)
public class ConfigFirewallPolicyParameters {

	private ArrayList<ConfigFirewallPolicyParameter> configFirewallPolicyParameters;

	public ArrayList<ConfigFirewallPolicyParameter> getConfigFirewallPolicyParameters() {
		return configFirewallPolicyParameters;
	}

	public void setConfigFirewallPolicyParameters(
			ArrayList<ConfigFirewallPolicyParameter> configFirewallPolicyParameters) {
		this.configFirewallPolicyParameters = configFirewallPolicyParameters;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
