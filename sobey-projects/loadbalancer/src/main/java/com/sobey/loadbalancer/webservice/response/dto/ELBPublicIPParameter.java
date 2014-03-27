package com.sobey.loadbalancer.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.loadbalancer.constans.WsConstants;

/**
 * Loadbalancer上,执行脚本所需参数ELBParameter的ELBPublicIPParameter对象:
 * 
 * <pre>
 * ipaddress  	公网IP地址
 * policyParameters	端口映射策略集合
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "ELBPublicIPParameter")
@XmlType(name = "ELBPublicIPParameter", namespace = WsConstants.NS)
public class ELBPublicIPParameter {

	/**
	 * 公网IP地址
	 */
	private String ipaddress;

	/**
	 * 端口映射策略集合
	 */
	private ArrayList<ELBPolicyParameter> policyParameters;

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public ArrayList<ELBPolicyParameter> getPolicyParameters() {
		return policyParameters;
	}

	public void setPolicyParameters(ArrayList<ELBPolicyParameter> policyParameters) {
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
