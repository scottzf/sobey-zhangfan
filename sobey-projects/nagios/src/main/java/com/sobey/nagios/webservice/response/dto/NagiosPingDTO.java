package com.sobey.nagios.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.nagios.constans.WsConstants;
import com.sobey.nagios.entity.NagiosPing;

@XmlRootElement(name = "NagiosPingDTO")
@XmlType(name = "NagiosPingDTO", namespace = WsConstants.NS)
public class NagiosPingDTO {

	private ArrayList<NagiosPing> nagiosPings;

	public ArrayList<NagiosPing> getNagiosPings() {
		return nagiosPings;
	}

	public void setNagiosPings(ArrayList<NagiosPing> nagiosPings) {
		this.nagiosPings = nagiosPings;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
