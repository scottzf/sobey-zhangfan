package com.sobey.dns.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.dns.constans.WsConstants;

/**
 * DNS上,执行脚本所需参数DNSParameter的DNSPublicIPParameter对象:
 * 
 * <pre>
 * ipaddress  	公网IP地址
 * policyParameters	端口映射策略集合
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "DNSPublicIPParameter")
@XmlType(name = "DNSPublicIPParameter", namespace = WsConstants.NS)
public class DNSPublicIPParameter {

	/**
	 * 公网IP地址
	 */
	private String ipaddress;

	/**
	 * 端口映射策略集合
	 */
	private ArrayList<DNSPolicyParameter> policyParameters;

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public ArrayList<DNSPolicyParameter> getPolicyParameters() {
		return policyParameters;
	}

	public void setPolicyParameters(ArrayList<DNSPolicyParameter> policyParameters) {
		this.policyParameters = policyParameters;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
