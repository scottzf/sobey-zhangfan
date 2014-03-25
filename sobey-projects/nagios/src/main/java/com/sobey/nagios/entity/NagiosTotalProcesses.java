package com.sobey.nagios.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NagiosTotalProcesses {

	private String ipaddress;

	private Date startTime;

	private Date endTime;

	private Integer Processes;

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

	public Integer getProcesses() {
		return Processes;
	}

	public void setProcesses(Integer processes) {
		Processes = processes;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
