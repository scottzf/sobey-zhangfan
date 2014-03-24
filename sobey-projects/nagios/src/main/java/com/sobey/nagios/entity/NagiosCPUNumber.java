package com.sobey.nagios.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NagiosCPUNumber {

	private String ipaddress;

	private Integer cpuNumber;

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Integer getCpuNumber() {
		return cpuNumber;
	}

	public void setCpuNumber(Integer cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
