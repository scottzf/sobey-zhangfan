package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.PortBasicDTO;

@XmlRootElement(name = "FirewallPortDTO")
@XmlType(name = "FirewallPortDTO", namespace = WsConstants.NS)
public class FirewallPortDTO extends PortBasicDTO {

	private Integer firewall;
	private FirewallDTO firewallDTO;;

	public Integer getFirewall() {
		return firewall;
	}

	public void setFirewall(Integer firewall) {
		this.firewall = firewall;
	}

	public FirewallDTO getFirewallDTO() {
		return firewallDTO;
	}

	public void setFirewallDTO(FirewallDTO firewallDTO) {
		this.firewallDTO = firewallDTO;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}