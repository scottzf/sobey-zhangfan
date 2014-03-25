package com.sobey.nagios.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.nagios.constans.WsConstants;
import com.sobey.nagios.entity.NagiosEth;

@XmlRootElement(name = "NagiosEthDTO")
@XmlType(name = "NagiosEthDTO", namespace = WsConstants.NS)
public class NagiosEthDTO {

	private ArrayList<NagiosEth> nagiosEths;

	public ArrayList<NagiosEth> getNagiosEths() {
		return nagiosEths;
	}

	public void setNagiosEths(ArrayList<NagiosEth> nagiosEths) {
		this.nagiosEths = nagiosEths;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
