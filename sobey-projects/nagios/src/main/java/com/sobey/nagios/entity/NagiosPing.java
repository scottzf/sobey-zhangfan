package com.sobey.nagios.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NagiosPing {

	private String ipaddress;

	private String startTime;

	private String endTime;

	private String packetLoss;

	private String rta;

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

	public String getPacketLoss() {
		return packetLoss;
	}

	public void setPacketLoss(String packetLoss) {
		this.packetLoss = packetLoss;
	}

	public String getRta() {
		return rta;
	}

	public void setRta(String rta) {
		this.rta = rta;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
