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
	private String diskTypeText;
	private Integer hdSize;
	private Integer rotationalSpeed;
	private String rotationalSpeedText;

	public Integer getDiskType() {
		return diskType;
	}

	public void setDiskType(Integer diskType) {
		this.diskType = diskType;
	}

	public String getDiskTypeText() {
		return diskTypeText;
	}

	public void setDiskTypeText(String diskTypeText) {
		this.diskTypeText = diskTypeText;
	}

	public Integer getHdSize() {
		return hdSize;
	}

	public void setHdSize(Integer hdSize) {
		this.hdSize = hdSize;
	}

	public Integer getRotationalSpeed() {
		return rotationalSpeed;
	}

	public void setRotationalSpeed(Integer rotationalSpeed) {
		this.rotationalSpeed = rotationalSpeed;
	}

	public String getRotationalSpeedText() {
		return rotationalSpeedText;
	}

	public void setRotationalSpeedText(String rotationalSpeedText) {
		this.rotationalSpeedText = rotationalSpeedText;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}