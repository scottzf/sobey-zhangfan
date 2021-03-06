package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.BasicEntity;

/**
 * DeviceSpec generated by hbm2java
 */
@Entity
@Table(name = "device_spec", schema = "public")
public class DeviceSpec extends BasicEntity {

	private Integer brand;
	private String cpuModel;
	private Integer cpuNumber;
	private Set<DeviceSpecHistory> deviceSpecHistories = new HashSet<DeviceSpecHistory>(0);
	private Integer deviceType;
	private Integer hdNumber;
	private Integer height;
	private Integer idc;
	private Integer maintenance;
	private Integer nicNumber;
	private Integer power;
	private Integer ramNumber;
	private String remark;

	public DeviceSpec() {
	}

	@Column(name = "brand")
	public Integer getBrand() {
		return brand;
	}

	@Column(name = "cpu_model")
	public String getCpuModel() {
		return cpuModel;
	}

	@Column(name = "cpu_number")
	public Integer getCpuNumber() {
		return cpuNumber;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "deviceSpec")
	public Set<DeviceSpecHistory> getDeviceSpecHistories() {
		return deviceSpecHistories;
	}

	@Column(name = "device_type")
	public Integer getDeviceType() {
		return deviceType;
	}

	@Column(name = "hd_number")
	public Integer getHdNumber() {
		return hdNumber;
	}

	@Column(name = "height")
	public Integer getHeight() {
		return height;
	}

	@Column(name = "idc")
	public Integer getIdc() {
		return idc;
	}

	@Column(name = "maintenance")
	public Integer getMaintenance() {
		return maintenance;
	}

	@Column(name = "nic_number")
	public Integer getNicNumber() {
		return nicNumber;
	}

	@Column(name = "power")
	public Integer getPower() {
		return power;
	}

	@Column(name = "ram_number")
	public Integer getRamNumber() {
		return ramNumber;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
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

	public void setDeviceSpecHistories(Set<DeviceSpecHistory> deviceSpecHistories) {
		this.deviceSpecHistories = deviceSpecHistories;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public void setHdNumber(Integer hdNumber) {
		this.hdNumber = hdNumber;
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

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
