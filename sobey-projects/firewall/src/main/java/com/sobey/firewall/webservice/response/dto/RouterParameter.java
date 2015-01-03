package com.sobey.firewall.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.firewall.constans.WsConstants;

/**
 * 防火墙上,绑定路由的对象
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "RouterParameter")
@XmlType(name = "RouterParameter", namespace = WsConstants.NS)
public class RouterParameter {

	/**
	 * 防火墙(路由)IP地址
	 */
	private String url;

	/**
	 * 防火墙(路由)登录名
	 */
	private String userName;

	/**
	 * 防火墙(路由)登录密码
	 */
	private String password;

	/**
	 * 绑定路由的子网集合
	 */
	private ArrayList<SubnetParameter> subnetParameters;
 
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<SubnetParameter> getSubnetParameters() {
		return subnetParameters;
	}

	public void setSubnetParameters(ArrayList<SubnetParameter> subnetParameters) {
		this.subnetParameters = subnetParameters;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	 
}
