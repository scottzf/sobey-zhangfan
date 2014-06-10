package com.sobey.nagios.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NagiosSwapUsage {

	private String ipaddress;

	private String startTime;

	private String endTime;

	private String FreePer;

	private String FreeSpace;

	private String TotalSpace;

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

	public String getFreePer() {
		return FreePer;
	}

	public void setFreePer(String freePer) {
		FreePer = freePer;
	}

	public String getFreeSpace() {
		return FreeSpace;
	}

	public void setFreeSpace(String freeSpace) {
		FreeSpace = freeSpace;
	}

	public String getTotalSpace() {
		return TotalSpace;
	}

	public void setTotalSpace(String totalSpace) {
		TotalSpace = totalSpace;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
