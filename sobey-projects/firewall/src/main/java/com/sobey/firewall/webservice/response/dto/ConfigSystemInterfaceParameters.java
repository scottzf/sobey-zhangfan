package com.sobey.firewall.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.firewall.constans.WsConstants;

@XmlRootElement(name = "ConfigSystemInterfaceParameters")
@XmlType(name = "ConfigSystemInterfaceParameters", namespace = WsConstants.NS)
public class ConfigSystemInterfaceParameters {

	private ArrayList<ConfigSystemInterfaceParameter> parameters;

	public ArrayList<ConfigSystemInterfaceParameter> getConfigSystemInterfaceParameters() {
		return parameters;
	}

	public void setConfigSystemInterfaceParameters(
			ArrayList<ConfigSystemInterfaceParameter> configSystemInterfaceParameters) {
		this.parameters = configSystemInterfaceParameters;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
