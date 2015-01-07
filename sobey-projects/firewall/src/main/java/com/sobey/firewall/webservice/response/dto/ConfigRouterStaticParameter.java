package com.sobey.firewall.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.firewall.constans.WsConstants;

@XmlRootElement(name = "ConfigRouterStaticParameter")
@XmlType(name = "ConfigRouterStaticParameter", namespace = WsConstants.NS)
public class ConfigRouterStaticParameter {

	/**
	 * 接口名
	 */
	private String interfaceName;

	/**
	 * ISP Gateway
	 */
	private String ispGateway;

	/**
	 * 路由Id ,递增唯一
	 */
	private Integer routerId;

	public String getInterfaceName() {
		return interfaceName;
	}

	public String getIspGateway() {
		return ispGateway;
	}

	public Integer getRouterId() {
		return routerId;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public void setIspGateway(String ispGateway) {
		this.ispGateway = ispGateway;
	}

	public void setRouterId(Integer routerId) {
		this.routerId = routerId;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
