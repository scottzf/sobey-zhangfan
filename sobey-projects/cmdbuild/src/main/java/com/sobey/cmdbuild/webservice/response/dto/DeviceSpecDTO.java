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
	private String brandText;
	private String cpuModel;
	private Integer cpuNumber;
	private Integer deviceType;
	private String deviceTypeText;
	private Integer hdnumber;
	private Integer height;
	private String hightText;
	private Integer maintenance;
	private String maintenanceText;
	private Integer nicNumber;
	private Integer power;
	private String powerText;
	private Integer ramNumber;

	public Integer getBrand() {
		return brand;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
	}

	public String getBrandText() {
		return brandText;
	}

	public void setBrandText(String brandText) {
		this.brandText = brandText;
	}

	public String getCpuModel() {
		return cpuModel;
	}

	public void setCpuModel(String cpuModel) {
		this.cpuModel = cpuModel;
	}

	public Integer getCpuNumber() {
		return cpuNumber;
	}

	public void setCpuNumber(Integer cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceTypeText() {
		return deviceTypeText;
	}

	public void setDeviceTypeText(String deviceTypeText) {
		this.deviceTypeText = deviceTypeText;
	}

	public Integer getHdnumber() {
		return hdnumber;
	}

	public void setHdnumber(Integer hdnumber) {
		this.hdnumber = hdnumber;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getHightText() {
		return hightText;
	}

	public void setHightText(String hightText) {
		this.hightText = hightText;
	}

	public Integer getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(Integer maintenance) {
		this.maintenance = maintenance;
	}

	public String getMaintenanceText() {
		return maintenanceText;
	}

	public void setMaintenanceText(String maintenanceText) {
		this.maintenanceText = maintenanceText;
	}

	public Integer getNicNumber() {
		return nicNumber;
	}

	public void setNicNumber(Integer nicNumber) {
		this.nicNumber = nicNumber;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public String getPowerText() {
		return powerText;
	}

	public void setPowerText(String powerText) {
		this.powerText = powerText;
	}

	public Integer getRamNumber() {
		return ramNumber;
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