package com.sobey.nagios.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.nagios.constans.WsConstants;
import com.sobey.nagios.entity.NagiosTotalProcesses;

@XmlRootElement(name = "NagiosTotalProcessesDTO")
@XmlType(name = "NagiosTotalProcessesDTO", namespace = WsConstants.NS)
public class NagiosTotalProcessesDTO {

	private ArrayList<NagiosTotalProcesses> nagiosTotalProcesses;

	public ArrayList<NagiosTotalProcesses> getNagiosTotalProcesses() {
		return nagiosTotalProcesses;
	}

	public void setNagiosTotalProcesses(ArrayList<NagiosTotalProcesses> nagiosTotalProcesses) {
		this.nagiosTotalProcesses = nagiosTotalProcesses;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
