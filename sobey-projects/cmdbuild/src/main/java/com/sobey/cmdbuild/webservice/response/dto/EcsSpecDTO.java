package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "EcsSpecDTO")
@XmlType(name = "EcsSpecDTO", namespace = WsConstants.NS)
public class EcsSpecDTO extends BasicDTO {

	private Integer cpuNumber;
	private Integer diskSize;
	private Integer idc;
	private String imageName;
	private Integer memory;
	private Integer osType;

	public Integer getCpuNumber() {
		return cpuNumber;
	}

	public Integer getDiskSize() {
		return diskSize;
	}

	public Integer getIdc() {
		return idc;
	}

	public String getImageName() {
		return imageName;
	}

	public Integer getMemory() {
		return memory;
	}

	public Integer getOsType() {
		return osType;
	}

	public void setCpuNumber(Integer cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public void setDiskSize(Integer diskSize) {
		this.diskSize = diskSize;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public void setOsType(Integer osType) {
		this.osType = osType;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}