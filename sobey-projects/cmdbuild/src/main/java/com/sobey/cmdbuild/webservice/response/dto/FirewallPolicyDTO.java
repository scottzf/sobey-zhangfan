package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "FirewallPolicyDTO")
@XmlType(name = "FirewallPolicyDTO", namespace = WsConstants.NS)
public class FirewallPolicyDTO extends BasicDTO {

	/**
	 * 行为 allow & deny
	 */
	private Integer action;

	/**
	 * IP地址, 下行时为源,上行时为目标
	 */
	private String address;

	/**
	 * 方向 上行 & 下行
	 */
	private Integer direction;

	/**
	 * 结束端口
	 */
	private Integer endPort;

	/**
	 * 所属FirewallService
	 */
	private Integer firewallService;

	/**
	 * 协议 TCP & UDP
	 */
	private Integer protocol;

	/**
	 * 起始端口
	 */
	private Integer startPort;

	public Integer getAction() {
		return action;
	}

	public String getAddress() {
		return address;
	}

	public Integer getDirection() {
		return direction;
	}

	public Integer getEndPort() {
		return endPort;
	}

	public Integer getFirewallService() {
		return firewallService;
	}

	public Integer getProtocol() {
		return protocol;
	}

	public Integer getStartPort() {
		return startPort;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public void setEndPort(Integer endPort) {
		this.endPort = endPort;
	}

	public void setFirewallService(Integer firewallService) {
		this.firewallService = firewallService;
	}

	public void setProtocol(Integer protocol) {
		this.protocol = protocol;
	}

	public void setStartPort(Integer startPort) {
		this.startPort = startPort;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}