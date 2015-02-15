package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ComponentBasicDTO;

@XmlRootElement(name = "NicDTO")
@XmlType(name = "NicDTO", namespace = WsConstants.NS)
public class NicDTO extends ComponentBasicDTO {

	private Integer nicRate;
	private Integer portNumber;
	private String virtualSwitchName;

	public Integer getNicRate() {
		return nicRate;
	}

	public Integer getPortNumber() {
		return portNumber;
	}

	public String getVirtualSwitchName() {
		return virtualSwitchName;
	}

	public void setNicRate(Integer nicRate) {
		this.nicRate = nicRate;
	}

	public void setPortNumber(Integer portNumber) {
		this.portNumber = portNumber;
	}

	public void setVirtualSwitchName(String virtualSwitchName) {
		this.virtualSwitchName = virtualSwitchName;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}