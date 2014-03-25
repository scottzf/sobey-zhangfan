package com.sobey.nagios.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.nagios.constans.WsConstants;
import com.sobey.nagios.entity.NagiosSystemOS;

@XmlRootElement(name = "NagiosSystemOSDTO")
@XmlType(name = "NagiosSystemOSDTO", namespace = WsConstants.NS)
public class NagiosSystemOSDTO {

	private ArrayList<NagiosSystemOS> nagiosSystemOSs;

	public ArrayList<NagiosSystemOS> getNagiosSystemOSs() {
		return nagiosSystemOSs;
	}

	public void setNagiosSystemOSs(ArrayList<NagiosSystemOS> nagiosSystemOSs) {
		this.nagiosSystemOSs = nagiosSystemOSs;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
