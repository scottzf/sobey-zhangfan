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
	 * 防火墙(路由)登录密码
	 */
	private String password;

	/**
	 * 路由Id ,递增唯一
	 */
	private Integer routerId;

	/**
	 * 防火墙(路由)IP地址
	 */
	private String url;

	/**
	 * 防火墙(路由)登录名
	 */
	private String userName;

	public String getInterfaceName() {
		return interfaceName;
	}

	public String getIspGateway() {
		return ispGateway;
	}

	public String getPassword() {
		return password;
	}

	public Integer getRouterId() {
		return routerId;
	}

	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public void setIspGateway(String ispGateway) {
		this.ispGateway = ispGateway;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRouterId(Integer routerId) {
		this.routerId = routerId;
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
