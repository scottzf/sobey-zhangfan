package com.sobey.nagios.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.nagios.constans.WsConstants;
import com.sobey.nagios.entity.NagiosCurrentUsers;

@XmlRootElement(name = "NagiosCurrentUsersDTO")
@XmlType(name = "NagiosCurrentUsersDTO", namespace = WsConstants.NS)
public class NagiosCurrentUsersDTO {

	private ArrayList<NagiosCurrentUsers> nagiosCurrentUsers;

	public ArrayList<NagiosCurrentUsers> getNagiosCurrentUsers() {
		return nagiosCurrentUsers;
	}

	public void setNagiosCurrentUsers(ArrayList<NagiosCurrentUsers> nagiosCurrentUsers) {
		this.nagiosCurrentUsers = nagiosCurrentUsers;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
