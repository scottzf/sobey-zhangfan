package com.sobey.nagios.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NagiosSystemOS {

	private String ipaddress;

	private String startTime;

	private String endTime;

	private String systemOS;

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSystemOS() {
		return systemOS;
	}

	public void setSystemOS(String systemOS) {
		this.systemOS = systemOS;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
