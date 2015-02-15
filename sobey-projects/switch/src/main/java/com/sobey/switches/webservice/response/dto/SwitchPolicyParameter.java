package com.sobey.switches.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.switches.constans.WsConstants;

/**
 * 在盛科交换机上,创建策略需要的对象
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "SwitchPolicyParameter")
@XmlType(name = "SwitchPolicyParameter", namespace = WsConstants.NS)
public class SwitchPolicyParameter {

	/**
	 * 宿主机IP
	 */
	private String hostIp;

	/**
	 * tunnel编号
	 */
	private Integer tunnelId;

	/**
	 * Vlan编号
	 */
	private Integer vlanId;

	public String getHostIp() {
		return hostIp;
	}

	public Integer getTunnelId() {
		return tunnelId;
	}

	public Integer getVlanId() {
		return vlanId;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public void setTunnelId(Integer tunnelId) {
		this.tunnelId = tunnelId;
	}

	public void setVlanId(Integer vlanId) {
		this.vlanId = vlanId;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
