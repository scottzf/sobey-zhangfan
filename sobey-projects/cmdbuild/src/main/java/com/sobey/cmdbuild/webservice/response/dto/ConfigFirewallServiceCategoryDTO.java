package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "ConfigFirewallServiceCategoryDTO")
@XmlType(name = "ConfigFirewallServiceCategoryDTO", namespace = WsConstants.NS)
public class ConfigFirewallServiceCategoryDTO extends BasicDTO {

	/**
	 * 行为 allow & deny
	 */
	private String action;

	/**
	 * IP地址, 下行时为源,上行时为目标
	 */
	private String address;

	/**
	 * 方向 上行 & 下行
	 */
	private String direction;

	/**
	 * 结束端口
	 */
	private Integer endPort;

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
	 * 协议 TCP & UDP
	 */
	private String protocol;

	/**
	 * 起始端口
	 */
	private Integer startPort;

	/**
	 * 所属Tenants
	 */
	private Integer tenants;

	private TenantsDTO tenantsDTO;

	public String getAction() {
		return action;
	}

	public String getAddress() {
		return address;
	}

	public String getDirection() {
		return direction;
	}

	public Integer getEndPort() {
		return endPort;
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

	public String getProtocol() {
		return protocol;
	}

	public Integer getStartPort() {
		return startPort;
	}

	public Integer getTenants() {
		return tenants;
	}

	public TenantsDTO getTenantsDTO() {
		return tenantsDTO;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setEndPort(Integer endPort) {
		this.endPort = endPort;
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

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setStartPort(Integer startPort) {
		this.startPort = startPort;
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