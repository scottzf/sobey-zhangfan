package com.sobey.nagios.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NagiosSwapUsage {

	private String ipaddress;

	private Date startDate;

	private Date endDate;

	private String FreePer;

	private String FreeSpace;

	private String TotalSpace;

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
