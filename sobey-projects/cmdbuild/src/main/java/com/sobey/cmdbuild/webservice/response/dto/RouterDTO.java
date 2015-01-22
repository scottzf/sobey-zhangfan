package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ServiceBasicDTO;

@XmlRootElement(name = "RouterDTO")
@XmlType(name = "RouterDTO", namespace = WsConstants.NS)
public class RouterDTO extends ServiceBasicDTO {

	private String cpuNumber;
	private String diskSize;
	private Integer firewallService;
	private Integer ipaddress;
	private String memorySize;
	private Integer server;

	public String getCpuNumber() {
		return cpuNumber;
	}

	public String getDiskSize() {
		return diskSize;
	}

	public Integer getFirewallService() {
		return firewallService;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public String getMemorySize() {
		return memorySize;
	}

	public Integer getServer() {
		return server;
	}

	public void setCpuNumber(String cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}

	public void setFirewallService(Integer firewallService) {
		this.firewallService = firewallService;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setMemorySize(String memorySize) {
		this.memorySize = memorySize;
	}

	public void setServer(Integer server) {
		this.server = server;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}