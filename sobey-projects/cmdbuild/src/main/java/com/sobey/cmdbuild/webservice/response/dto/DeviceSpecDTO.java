package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement(name = "DeviceSpecDTO")
@XmlType(name = "DeviceSpecDTO", namespace = WsConstants.NS)
public class DeviceSpecDTO {

	private Integer id;
	private String code;
	private String description;
	private Date beginDate;
	private String remark;
	private Integer deviceType;
	private String deviceTypeText;
	private Integer brand;
	private String brandText;
	private Integer power;
	private String powerText;
	private Integer maintenance;
	private String maintenanceText;
	private Integer hight;
	private String hightText;
	private Integer ramNumber;
	private Integer cpuNumber;
	private Integer nicNumber;
	private Integer hdnumber;
	private String cpuModel;
	private Double price;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getHight() {
		return hight;
	}

	public void setHight(Integer hight) {
		this.hight = hight;
	}

	public String getHightText() {
		return hightText;
	}

	public void setHightText(String hightText) {
		this.hightText = hightText;
	}

	public Integer getRamNumber() {
		return ramNumber;
	}

	public void setRamNumber(Integer ramNumber) {
		this.ramNumber = ramNumber;
	}

	public Integer getCpuNumber() {
		return cpuNumber;
	}

	public void setCpuNumber(Integer cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public Integer getNicNumber() {
		return nicNumber;
	}

	public void setNicNumber(Integer nicNumber) {
		this.nicNumber = nicNumber;
	}

	public Integer getHdnumber() {
		return hdnumber;
	}

	public void setHdnumber(Integer hdnumber) {
		this.hdnumber = hdnumber;
	}

	public String getCpuModel() {
		return cpuModel;
	}

	public void setCpuModel(String cpuModel) {
		this.cpuModel = cpuModel;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}