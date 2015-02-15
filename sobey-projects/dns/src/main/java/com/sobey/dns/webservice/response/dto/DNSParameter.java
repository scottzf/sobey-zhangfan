package com.sobey.dns.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.dns.constans.WsConstants;

/**
 * DNS上,执行脚本所需参数的DNSParameter对象:
 * 
 * <pre>
 * domianName	域名
 * domianType	域名类型: GSLB,A,CNAME
 * publicIPs	公网IP集合
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "DNSParameter")
@XmlType(name = "DNSParameter", namespace = WsConstants.NS)
public class DNSParameter {

	/**
	 * 域名
	 */
	private String domianName;

	/**
	 * 域名类型: GSLB,A,CNAME
	 */
	private String domianType;

	/**
	 * netscaler登录密码
	 */
	private String password;

	/**
	 * 端口
	 */
	private Integer port;

	/**
	 * 登录协议, HTTP
	 */
	private String protocol;

	/**
	 * 公网IP集合
	 */
	private ArrayList<DNSPublicIPParameter> publicIPs;

	/**
	 * netscaler IP地址
	 */
	private String url;

	/**
	 * netscaler登录名
	 */
	private String userName;

	public String getDomianName() {
		return domianName;
	}

	public String getDomianType() {
		return domianType;
	}

	public String getPassword() {
		return password;
	}

	public Integer getPort() {
		return port;
	}

	public String getProtocol() {
		return protocol;
	}

	public ArrayList<DNSPublicIPParameter> getPublicIPs() {
		return publicIPs;
	}

	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public void setDomianName(String domianName) {
		this.domianName = domianName;
	}

	public void setDomianType(String domianType) {
		this.domianType = domianType;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setPublicIPs(ArrayList<DNSPublicIPParameter> publicIPs) {
		this.publicIPs = publicIPs;
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
