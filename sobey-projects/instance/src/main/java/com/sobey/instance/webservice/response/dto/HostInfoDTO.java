package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

@XmlRootElement(name = "HostInfoDTO")
@XmlType(name = "HostInfoDTO", namespace = WsConstants.NS)
public class HostInfoDTO {

	/**
	 * cpu频率(GHZ)
	 */
	private String cpuHz;

	/**
	 * CPU数量
	 */
	private String cpuNumber;

	/**
	 * 宿主机的ID
	 */
	private String hostId;

	/**
	 * 宿主机名称
	 */
	private String hostName;

	/**
	 * 内存大小(MB)
	 */
	private String memoryMB;

	/**
	 * 型号
	 */
	private String model;

	/**
	 * 宿主机在vCenter中,resourcePool的Id
	 */
	private String resourcePool;

	/**
	 * 厂商
	 */
	private String vendor;

	public String getCpuHz() {
		return cpuHz;
	}

	public String getCpuNumber() {
		return cpuNumber;
	}

	public String getHostId() {
		return hostId;
	}

	public String getHostName() {
		return hostName;
	}

	public String getMemoryMB() {
		return memoryMB;
	}

	public String getModel() {
		return model;
	}

	public String getResourcePool() {
		return resourcePool;
	}

	public String getVendor() {
		return vendor;
	}

	public void setCpuHz(String cpuHz) {
		this.cpuHz = cpuHz;
	}

	public void setCpuNumber(String cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setMemoryMB(String memoryMB) {
		this.memoryMB = memoryMB;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setResourcePool(String resourcePool) {
		this.resourcePool = resourcePool;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}