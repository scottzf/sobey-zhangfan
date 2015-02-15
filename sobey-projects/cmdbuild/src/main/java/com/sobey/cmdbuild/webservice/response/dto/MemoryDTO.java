package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ComponentBasicDTO;

@XmlRootElement(name = "MemoryDTO")
@XmlType(name = "MemoryDTO", namespace = WsConstants.NS)
public class MemoryDTO extends ComponentBasicDTO {

	private Integer frequency;
	private Integer size;

	public Integer getFrequency() {
		return frequency;
	}

	public Integer getSize() {
		return size;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}