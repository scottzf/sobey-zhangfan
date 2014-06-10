package com.sobey.nagios.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.nagios.constans.WsConstants;
import com.sobey.nagios.entity.NagiosMemoryLoad;

@XmlRootElement(name = "NagiosMemoryLoadDTO")
@XmlType(name = "NagiosMemoryLoadDTO", namespace = WsConstants.NS)
public class NagiosMemoryLoadDTO {

	private ArrayList<NagiosMemoryLoad> nagiosMemoryLoads;

	public ArrayList<NagiosMemoryLoad> getNagiosMemoryLoads() {
		return nagiosMemoryLoads;
	}

	public void setNagiosMemoryLoads(ArrayList<NagiosMemoryLoad> nagiosMemoryLoads) {
		this.nagiosMemoryLoads = nagiosMemoryLoads;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
