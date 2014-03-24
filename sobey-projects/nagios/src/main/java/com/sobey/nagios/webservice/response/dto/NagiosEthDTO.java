package com.sobey.nagios.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.nagios.constans.WsConstants;

@XmlRootElement(name = "NagiosEthDTO")
@XmlType(name = "NagiosEthDTO", namespace = WsConstants.NS)
public class NagiosEthDTO {

	private String ipaddress;

	private Date startDate;

	private Date endDate;

	private String TrafficIn;

	private String TrafficOut;

	private String TrafficTotal;

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

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
