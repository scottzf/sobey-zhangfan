package com.sobey.nagios.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.nagios.constans.WsConstants;
import com.sobey.nagios.entity.NagiosDiskIO;

@XmlRootElement(name = "NagiosDiskIODTO")
@XmlType(name = "NagiosDiskIODTO", namespace = WsConstants.NS)
public class NagiosDiskIODTO {

	private ArrayList<NagiosDiskIO> nagiosDiskIOs;

	public ArrayList<NagiosDiskIO> getNagiosDiskIOs() {
		return nagiosDiskIOs;
	}

	public void setNagiosDiskIOs(ArrayList<NagiosDiskIO> nagiosDiskIOs) {
		this.nagiosDiskIOs = nagiosDiskIOs;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
