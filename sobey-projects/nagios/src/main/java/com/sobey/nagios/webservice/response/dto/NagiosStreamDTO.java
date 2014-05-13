package com.sobey.nagios.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.nagios.constans.WsConstants;
import com.sobey.nagios.entity.NagiosStream;

@XmlRootElement(name = "NagiosStreamDTO")
@XmlType(name = "NagiosStreamDTO", namespace = WsConstants.NS)
public class NagiosStreamDTO {

	private ArrayList<NagiosStream> nagiosStreams;

	public ArrayList<NagiosStream> getNagiosStreams() {
		return nagiosStreams;
	}

	public void setNagiosStreams(ArrayList<NagiosStream> nagiosStreams) {
		this.nagiosStreams = nagiosStreams;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
