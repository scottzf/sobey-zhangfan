package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "EcsSpecDTO")
@XmlType(name = "EcsSpecDTO", namespace = WsConstants.NS)
public class EcsSpecDTO extends BasicDTO {

	private Integer idc;
	private String imageName;
	private Integer osType;
	private Integer producedNumber;

	public Integer getIdc() {
		return idc;
	}

	public String getImageName() {
		return imageName;
	}

	public Integer getOsType() {
		return osType;
	}

	public Integer getProducedNumber() {
		return producedNumber;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setOsType(Integer osType) {
		this.osType = osType;
	}

	public void setProducedNumber(Integer producedNumber) {
		this.producedNumber = producedNumber;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}