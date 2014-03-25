package com.sobey.nagios.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NagiosCPULoad {

	private String ipaddress;

	private Date startTime;

	private Date endTime;

	private String average5;

	private String average10;

	private String average15;

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

	public String getAverage5() {
		return average5;
	}

	public void setAverage5(String average5) {
		this.average5 = average5;
	}

	public String getAverage10() {
		return average10;
	}

	public void setAverage10(String average10) {
		this.average10 = average10;
	}

	public String getAverage15() {
		return average15;
	}

	public void setAverage15(String average15) {
		this.average15 = average15;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
