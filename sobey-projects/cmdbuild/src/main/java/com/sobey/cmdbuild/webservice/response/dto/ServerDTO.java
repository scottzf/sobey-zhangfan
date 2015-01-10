package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.DeviceBasicDTO;

@XmlRootElement(name = "ServerDTO")
@XmlType(name = "ServerDTO", namespace = WsConstants.NS)
public class ServerDTO extends DeviceBasicDTO {

	private String cpuHz;
	private String cpuNumber;
	private String hostgroup;
	private String memorySize;
	private String model;
	private String resgroup;
	private String vendor;

	public String getCpuHz() {
		return cpuHz;
	}

	public String getCpuNumber() {
		return cpuNumber;
	}

	public String getHostgroup() {
		return hostgroup;
	}

	public String getMemorySize() {
		return memorySize;
	}

	public String getModel() {
		return model;
	}

	public String getResgroup() {
		return resgroup;
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

	public void setHostgroup(String hostgroup) {
		this.hostgroup = hostgroup;
	}

	public void setMemorySize(String memorySize) {
		this.memorySize = memorySize;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setResgroup(String resgroup) {
		this.resgroup = resgroup;
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