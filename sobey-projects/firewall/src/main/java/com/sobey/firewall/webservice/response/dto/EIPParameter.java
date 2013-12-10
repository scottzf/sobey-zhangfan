package com.sobey.firewall.webservice.response.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;
import com.sobey.firewall.constans.WsConstants;

/**
 * 防火墙上,执行脚本所需参数的EIPParameter对象:
 * 
 * <pre>
 * internetIP	公网IP
 * privateIP	内网IP
 * policyParameters	映射策略集合
 * isp		ISP运营商 0: 中国电信 1:中国联通
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "EIPParameter")
@XmlType(name = "EIPParameter", namespace = WsConstants.NS)
public class EIPParameter {

	/**
	 * 公网IP
	 */
	private String internetIP;

	/**
	 * ISP运营商 0: 中国电信 1:中国联通
	 */
	private Integer isp;

	/**
	 * 内网IP
	 */
	private String privateIP;

	/**
	 * 映射策略集合
	 */
	private List<EIPPolicyParameter> policies = Lists.newArrayList();

	public String getInternetIP() {
		return internetIP;
	}

	public void setInternetIP(String internetIP) {
		this.internetIP = internetIP;
	}

	public Integer getIsp() {
		return isp;
	}

	public void setIsp(Integer isp) {
		this.isp = isp;
	}

	public String getPrivateIP() {
		return privateIP;
	}

	public void setPrivateIP(String privateIP) {
		this.privateIP = privateIP;
	}

	public List<EIPPolicyParameter> getPolicies() {
		return policies;
	}

	public void setPolicies(List<EIPPolicyParameter> policies) {
		this.policies = policies;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
