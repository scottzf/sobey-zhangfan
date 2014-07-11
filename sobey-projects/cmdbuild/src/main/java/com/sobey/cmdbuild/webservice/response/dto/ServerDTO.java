package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.DeviceBasicDTO;

@XmlRootElement(name = "ServerDTO")
@XmlType(name = "ServerDTO", namespace = WsConstants.NS)
public class ServerDTO extends DeviceBasicDTO {

	private String resgroup;

	public String getResgroup() {
		return resgroup;
	}

	public void setResgroup(String resgroup) {
		this.resgroup = resgroup;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}