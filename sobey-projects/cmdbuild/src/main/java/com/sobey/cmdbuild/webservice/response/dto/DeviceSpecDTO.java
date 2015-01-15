package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "DeviceSpecDTO")
@XmlType(name = "DeviceSpecDTO", namespace = WsConstants.NS)
public class DeviceSpecDTO extends BasicDTO {

	private Integer brand;
	private String cpuModel;
	private Integer cpuNumber;
	private Integer deviceType;
	private Integer hdnumber;
	private Integer height;
	private Integer idc;
	private Integer maintenance;
	private Integer nicNumber;
	private Integer power;
	private Integer ramNumber;

	public Integer getBrand() {
		return brand;
	}

	public String getCpuModel() {
		return cpuModel;
	}

	public Integer getCpuNumber() {
		return cpuNumber;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public Integer getHdnumber() {
		return hdnumber;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getIdc() {
		return idc;
	}

	public Integer getMaintenance() {
		return maintenance;
	}

	public Integer getNicNumber() {
		return nicNumber;
	}

	public Integer getPower() {
		return power;
	}

	public Integer getRamNumber() {
		return ramNumber;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
	}

	public void setCpuModel(String cpuModel) {
		this.cpuModel = cpuModel;
	}

	public void setCpuNumber(Integer cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public void setHdnumber(Integer hdnumber) {
		this.hdnumber = hdnumber;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setMaintenance(Integer maintenance) {
		this.maintenance = maintenance;
	}

	public void setNicNumber(Integer nicNumber) {
		this.nicNumber = nicNumber;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public void setRamNumber(Integer ramNumber) {
		this.ramNumber = ramNumber;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}