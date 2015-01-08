package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "ConfigFirewallPolicyDTO")
@XmlType(name = "ConfigFirewallPolicyDTO", namespace = WsConstants.NS)
public class ConfigFirewallPolicyDTO extends BasicDTO {

	/**
	 * 目标地址
	 */
	private String dstaddr;

	/**
	 * 目标接口
	 */
	private String dstintf;

	/**
	 * 所属FirewallService
	 */
	private Integer firewallService;

	private FirewallServiceDTO firewallServiceDTO;

	/**
	 * 防火墙策略ID,唯一递增
	 */
	private Integer policyId;

	/**
	 * 策略类型:Internet or Subnet
	 */
	private Integer policyType;

	private String policyTypeText;

	/**
	 * 源地址
	 */
	private String srcaddr;

	/**
	 * 源接口
	 */
	private String srcintf;

	/**
	 * 所属Tenants
	 */
	private Integer tenants;

	private TenantsDTO tenantsDTO;

	public String getDstaddr() {
		return dstaddr;
	}

	public String getDstintf() {
		return dstintf;
	}

	public Integer getFirewallService() {
		return firewallService;
	}

	public FirewallServiceDTO getFirewallServiceDTO() {
		return firewallServiceDTO;
	}

	public Integer getPolicyId() {
		return policyId;
	}

	public Integer getPolicyType() {
		return policyType;
	}

	public String getPolicyTypeText() {
		return policyTypeText;
	}

	public String getSrcaddr() {
		return srcaddr;
	}

	public String getSrcintf() {
		return srcintf;
	}

	public Integer getTenants() {
		return tenants;
	}

	public TenantsDTO getTenantsDTO() {
		return tenantsDTO;
	}

	public void setDstaddr(String dstaddr) {
		this.dstaddr = dstaddr;
	}

	public void setDstintf(String dstintf) {
		this.dstintf = dstintf;
	}

	public void setFirewallService(Integer firewallService) {
		this.firewallService = firewallService;
	}

	public void setFirewallServiceDTO(FirewallServiceDTO firewallServiceDTO) {
		this.firewallServiceDTO = firewallServiceDTO;
	}

	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}

	public void setPolicyType(Integer policyType) {
		this.policyType = policyType;
	}

	public void setPolicyTypeText(String policyTypeText) {
		this.policyTypeText = policyTypeText;
	}

	public void setSrcaddr(String srcaddr) {
		this.srcaddr = srcaddr;
	}

	public void setSrcintf(String srcintf) {
		this.srcintf = srcintf;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	public void setTenantsDTO(TenantsDTO tenantsDTO) {
		this.tenantsDTO = tenantsDTO;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}