package com.sobey.nagios.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.nagios.constans.WsConstants;

@XmlRootElement(name = "NagiosSystemOSDTO")
@XmlType(name = "NagiosSystemOSDTO", namespace = WsConstants.NS)
public class NagiosSystemOSDTO {

	private String ipaddress;

	private String systemOS;

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getSystemOS() {
		return systemOS;
	}

	public void setSystemOS(String systemOS) {
		this.systemOS = systemOS;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
