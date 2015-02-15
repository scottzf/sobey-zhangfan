package com.sobey.loadbalancer.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.loadbalancer.constans.WsConstants;

/**
 * Loadbalancer上,执行脚本所需参数的ELBParameter对象:
 * 
 * <pre>
 * vip		虚拟IP
 * publicIPs	公网IP集合
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "ELBParameter")
@XmlType(name = "ELBParameter", namespace = WsConstants.NS)
public class ELBParameter {

	/**
	 * netscaler IP地址
	 */
	private String url;

	/**
	 * netscaler登录名
	 */
	private String userName;

	/**
	 * netscaler登录密码
	 */
	private String password;

	/**
	 * 登录协议, HTTP
	 */
	private String protocol;

	/**
	 * 端口
	 */
	private Integer port;

	/**
	 * elb的VIP
	 */
	private String vip;

	/**
	 * IP集合
	 */
	private ArrayList<ELBPublicIPParameter> publicIPs;

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

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public ArrayList<ELBPublicIPParameter> getPublicIPs() {
		return publicIPs;
	}

	public void setPublicIPs(ArrayList<ELBPublicIPParameter> publicIPs) {
		this.publicIPs = publicIPs;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
