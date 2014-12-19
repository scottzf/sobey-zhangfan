package com.sobey.instance.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

@XmlRootElement(name = "HostsDTO")
@XmlType(name = "HostsDTO", namespace = WsConstants.NS)
public class HostsDTO {

	private ArrayList<String> hostName;

	public ArrayList<String> getHostName() {
		return hostName;
	}

	public void setHostName(ArrayList<String> hostName) {
		this.hostName = hostName;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}