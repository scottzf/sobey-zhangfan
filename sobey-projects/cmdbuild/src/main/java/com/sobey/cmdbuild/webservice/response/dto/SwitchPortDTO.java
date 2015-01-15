package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.PortBasicDTO;

@XmlRootElement(name = "SwitchPortDTO")
@XmlType(name = "SwitchPortDTO", namespace = WsConstants.NS)
public class SwitchPortDTO extends PortBasicDTO {

	private Integer switches;

	public Integer getSwitches() {
		return switches;
	}

	public void setSwitches(Integer switches) {
		this.switches = switches;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}