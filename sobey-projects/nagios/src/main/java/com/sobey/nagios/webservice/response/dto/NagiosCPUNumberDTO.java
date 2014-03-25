package com.sobey.nagios.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.nagios.constans.WsConstants;
import com.sobey.nagios.entity.NagiosCPUNumber;

@XmlRootElement(name = "NagiosCPUNumberDTO")
@XmlType(name = "NagiosCPUNumberDTO", namespace = WsConstants.NS)
public class NagiosCPUNumberDTO {

	private ArrayList<NagiosCPUNumber> nagiosCPUNumbers;

	public ArrayList<NagiosCPUNumber> getNagiosCPUNumbers() {
		return nagiosCPUNumbers;
	}

	public void setNagiosCPUNumbers(ArrayList<NagiosCPUNumber> nagiosCPUNumbers) {
		this.nagiosCPUNumbers = nagiosCPUNumbers;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
