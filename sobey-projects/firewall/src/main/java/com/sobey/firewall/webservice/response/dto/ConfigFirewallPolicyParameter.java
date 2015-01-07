package com.sobey.firewall.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.firewall.constans.WsConstants;

@XmlRootElement(name = "ConfigFirewallPolicyParameter")
@XmlType(name = "ConfigFirewallPolicyParameter", namespace = WsConstants.NS)
public class ConfigFirewallPolicyParameter {

	/**
	 * 目标地址,对外的情况填写"ALL"
	 */
	private String dstaddr;

	/**
	 * 目标接口
	 */
	private String dstintf;

	/**
	 * 防火墙策略ID,唯一递增
	 */
	private Integer policyId;

	/**
	 * 策略类型:Internet or Subnet
	 */
	private String policyType;

	/**
	 * 源地址
	 */
	private String srcaddr;

	/**
	 * 源接口
	 */
	private String srcintf;

	public String getDstaddr() {
		return dstaddr;
	}

	public String getDstintf() {
		return dstintf;
	}

	public Integer getPolicyId() {
		return policyId;
	}

	public String getPolicyType() {
		return policyType;
	}

	public String getSrcaddr() {
		return srcaddr;
	}

	public String getSrcintf() {
		return srcintf;
	}

	public void setDstaddr(String dstaddr) {
		this.dstaddr = dstaddr;
	}

	public void setDstintf(String dstintf) {
		this.dstintf = dstintf;
	}

	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public void setSrcaddr(String srcaddr) {
		this.srcaddr = srcaddr;
	}

	public void setSrcintf(String srcintf) {
		this.srcintf = srcintf;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
