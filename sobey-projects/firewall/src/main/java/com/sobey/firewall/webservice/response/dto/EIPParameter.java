package com.sobey.firewall.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.firewall.constans.WsConstants;

/**
 * 防火墙上,执行脚本所需参数的EIPParameter对象:
 * 
 * <pre>
 * internetIP	公网IP
 * privateIP	内网IP
 * policyParameters	映射策略集合
 * isp		ISP运营商 0: 中国电信 1:中国联通
 * allPolicies	所有映射策略集合
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "EIPParameter")
@XmlType(name = "EIPParameter", namespace = WsConstants.NS)
public class EIPParameter {

	/**
	 * 所有映射策略集合
	 */
	private ArrayList<String> allPolicies;

	/**
	 * 公网IP
	 */
	private String internetIP;

	/**
	 * ISP运营商 0: 中国电信 1:中国联通
	 */
	private Integer isp;

	/**
	 * 防火墙(路由)登录密码
	 */
	private String password;

	/**
	 * 映射策略集合
	 */
	private ArrayList<EIPPolicyParameter> policies;

	/**
	 * 内网IP
	 */
	private String privateIP;

	/**
	 * 防火墙(路由)IP地址
	 */
	private String url;

	/**
	 * 防火墙(路由)登录名
	 */
	private String userName;

	public ArrayList<String> getAllPolicies() {
		return allPolicies;
	}

	public String getInternetIP() {
		return internetIP;
	}

	public Integer getIsp() {
		return isp;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<EIPPolicyParameter> getPolicies() {
		return policies;
	}

	public String getPrivateIP() {
		return privateIP;
	}

	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public void setAllPolicies(ArrayList<String> allPolicies) {
		this.allPolicies = allPolicies;
	}

	public void setInternetIP(String internetIP) {
		this.internetIP = internetIP;
	}

	public void setIsp(Integer isp) {
		this.isp = isp;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPolicies(ArrayList<EIPPolicyParameter> policies) {
		this.policies = policies;
	}

	public void setPrivateIP(String privateIP) {
		this.privateIP = privateIP;
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
