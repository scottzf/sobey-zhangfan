package com.sobey.nagios.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NagiosEth {

	private String ipaddress;

	private String startTime;

	private String endTime;

	private String TrafficIn;

	private String TrafficOut;

	private String TrafficTotal;

	private String interval;

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

	public String getTrafficIn() {
		return TrafficIn;
	}

	public void setTrafficIn(String trafficIn) {
		TrafficIn = trafficIn;
	}

	public String getTrafficOut() {
		return TrafficOut;
	}

	public void setTrafficOut(String trafficOut) {
		TrafficOut = trafficOut;
	}

	public String getTrafficTotal() {
		return TrafficTotal;
	}

	public void setTrafficTotal(String trafficTotal) {
		TrafficTotal = trafficTotal;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
