package com.sobey.nagios.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NagiosRootPartition {

	private String ipaddress;

	private Date startTime;

	private Date endTime;

	private String FreePer;

	private String FreeSpace;

	private String Inode;

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
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

	public String getInode() {
		return Inode;
	}

	public void setInode(String inode) {
		Inode = inode;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
