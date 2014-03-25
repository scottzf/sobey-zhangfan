package com.sobey.nagios.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.nagios.constans.WsConstants;
import com.sobey.nagios.entity.NagiosCPULoad;

@XmlRootElement(name = "NagiosCPULoadDTO")
@XmlType(name = "NagiosCPULoadDTO", namespace = WsConstants.NS)
public class NagiosCPULoadDTO {

	private ArrayList<NagiosCPULoad> nagiosCPULoads;

	public ArrayList<NagiosCPULoad> getNagiosCPULoads() {
		return nagiosCPULoads;
	}

	public void setNagiosCPULoads(ArrayList<NagiosCPULoad> nagiosCPULoads) {
		this.nagiosCPULoads = nagiosCPULoads;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
