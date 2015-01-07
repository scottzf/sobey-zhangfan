package com.sobey.firewall.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.firewall.constans.WsConstants;

@XmlRootElement(name = "ConfigRouterStaticParameters")
@XmlType(name = "ConfigRouterStaticParameters", namespace = WsConstants.NS)
public class ConfigRouterStaticParameters {

	private ArrayList<ConfigRouterStaticParameter> configRouterStaticParameters;

	public ArrayList<ConfigRouterStaticParameter> getConfigRouterStaticParameters() {
		return configRouterStaticParameters;
	}

	public void setConfigRouterStaticParameters(ArrayList<ConfigRouterStaticParameter> configRouterStaticParameters) {
		this.configRouterStaticParameters = configRouterStaticParameters;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
