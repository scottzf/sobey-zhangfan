package com.sobey.storage.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.storage.constans.WsConstants;

@XmlRootElement(name = "VolumeInfoDTO")
@XmlType(name = "VolumeInfoDTO", namespace = WsConstants.NS)
public class VolumeInfoDTO {

	private String name;
	private String totalSize;
	private String usedSize;
	private String AvailableSize;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	public String getUsedSize() {
		return usedSize;
	}

	public void setUsedSize(String usedSize) {
		this.usedSize = usedSize;
	}

	public String getAvailableSize() {
		return AvailableSize;
	}

	public void setAvailableSize(String availableSize) {
		AvailableSize = availableSize;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}