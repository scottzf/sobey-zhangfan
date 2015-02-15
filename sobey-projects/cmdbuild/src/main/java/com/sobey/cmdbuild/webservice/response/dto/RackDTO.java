package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "RackDTO")
@XmlType(name = "RackDTO", namespace = WsConstants.NS)
public class RackDTO extends BasicDTO {

	private Integer brand;
	private String gdzcSn;
	private Integer height;
	private Integer idc;
	private String model;
	private Integer power;
	private String sn;
	private Integer unitNumber;

	public Integer getBrand() {
		return brand;
	}

	public String getGdzcSn() {
		return gdzcSn;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getIdc() {
		return idc;
	}

	public String getModel() {
		return model;
	}

	public Integer getPower() {
		return power;
	}

	public String getSn() {
		return sn;
	}

	public Integer getUnitNumber() {
		return unitNumber;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
	}

	public void setGdzcSn(String gdzcSn) {
		this.gdzcSn = gdzcSn;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public void setUnitNumber(Integer unitNumber) {
		this.unitNumber = unitNumber;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}