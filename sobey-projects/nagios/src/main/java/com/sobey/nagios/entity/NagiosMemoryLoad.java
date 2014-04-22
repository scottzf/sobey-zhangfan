package com.sobey.nagios.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NagiosMemoryLoad {

	private String ipaddress;

	private String startTime;

	private String endTime;

	private String UsedPer;

	private String Userd;

	private String Total;

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
