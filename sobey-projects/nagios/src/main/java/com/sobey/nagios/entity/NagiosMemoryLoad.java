package com.sobey.nagios.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NagiosMemoryLoad {

	private String ipaddress;

	private Date startTime;

	private Date endTime;

	private String UsedPer;

	private String Userd;

	private String Total;

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

	public String getUsedPer() {
		return UsedPer;
	}

	public void setUsedPer(String usedPer) {
		UsedPer = usedPer;
	}

	public String getUserd() {
		return Userd;
	}

	public void setUserd(String userd) {
		Userd = userd;
	}

	public String getTotal() {
		return Total;
	}

	public void setTotal(String total) {
		Total = total;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
