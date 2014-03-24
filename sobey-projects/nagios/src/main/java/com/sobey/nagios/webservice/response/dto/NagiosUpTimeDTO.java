package com.sobey.nagios.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.nagios.constans.WsConstants;

@XmlRootElement(name = "NagiosUpTimeDTO")
@XmlType(name = "NagiosUpTimeDTO", namespace = WsConstants.NS)
public class NagiosUpTimeDTO {

	private String ipaddress;

	private Date UpTime;

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Date getUpTime() {
		return UpTime;
	}

	public void setUpTime(Date upTime) {
		UpTime = upTime;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
