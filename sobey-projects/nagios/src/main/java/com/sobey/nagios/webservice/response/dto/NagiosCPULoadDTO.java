package com.sobey.nagios.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.nagios.constans.WsConstants;

@XmlRootElement(name = "NagiosCPULoadDTO")
@XmlType(name = "NagiosCPULoadDTO", namespace = WsConstants.NS)
public class NagiosCPULoadDTO {

	private String ipaddress;

	private Date startDate;

	private Date endDate;

	private String average5;

	private String average10;

	private String average15;

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
