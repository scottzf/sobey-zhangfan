package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ComponentBasicDTO;

@XmlRootElement(name = "StorageBoxDTO")
@XmlType(name = "StorageBoxDTO", namespace = WsConstants.NS)
public class StorageBoxDTO extends ComponentBasicDTO {

	private Integer diskNumber;
	private Integer diskType;
	private String diskTypeText;

	public Integer getDiskNumber() {
		return diskNumber;
	}

	public void setDiskNumber(Integer diskNumber) {
		this.diskNumber = diskNumber;
	}

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

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}