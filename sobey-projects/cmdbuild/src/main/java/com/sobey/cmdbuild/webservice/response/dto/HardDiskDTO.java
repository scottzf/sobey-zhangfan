package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ComponentBasicDTO;

@XmlRootElement(name = "HardDiskDTO")
@XmlType(name = "HardDiskDTO", namespace = WsConstants.NS)
public class HardDiskDTO extends ComponentBasicDTO {

	private Integer diskType;
	private Integer hdSize;
	private Integer rotationalSpeed;

	public Integer getDiskType() {
		return diskType;
	}

	public Integer getHdSize() {
		return hdSize;
	}

	public Integer getRotationalSpeed() {
		return rotationalSpeed;
	}

	public void setDiskType(Integer diskType) {
		this.diskType = diskType;
	}

	public void setHdSize(Integer hdSize) {
		this.hdSize = hdSize;
	}

	public void setRotationalSpeed(Integer rotationalSpeed) {
		this.rotationalSpeed = rotationalSpeed;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}