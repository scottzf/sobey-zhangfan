package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	private Integer maintenance;
	private Integer nicNumber;
	private Integer power;
	private Double price;
	private Integer ramNumber;
	private String remark;

	public DeviceSpec() {
	}

	@Column(name = "\"Brand\"")
	public Integer getBrand() {
		return this.brand;
	}

	@Column(name = "cpu_model", length = 100)
	public String getCpuModel() {
		return this.cpuModel;
	}

	@Column(name = "cpu_number")
	public Integer getCpuNumber() {
		return this.cpuNumber;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "deviceSpec")
	public Set<DeviceSpecHistory> getDeviceSpecHistories() {
		return this.deviceSpecHistories;
	}

	@Column(name = "device_type")
	public Integer getDeviceType() {
		return this.deviceType;
	}

	@Column(name = "hd_number")
	public Integer getHdNumber() {
		return this.hdNumber;
	}

	@Column(name = "\"Height\"")
	public Integer getHeight() {
		return this.height;
	}

	@Column(name = "\"Maintenance\"")
	public Integer getMaintenance() {
		return this.maintenance;
	}

	@Column(name = "nic_number")
	public Integer getNicNumber() {
		return this.nicNumber;
	}

	@Column(name = "\"Power\"")
	public Integer getPower() {
		return this.power;
	}

	@Column(name = "\"Price\"", precision = 17, scale = 17)
	public Double getPrice() {
		return this.price;
	}

	@Column(name = "ram_number")
	public Integer getRamNumber() {
		return this.ramNumber;
	}

	@Column(name = "\"Remark\"", length = 250)
	public String getRemark() {
		return this.remark;
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

	public void setMaintenance(Integer maintenance) {
		this.maintenance = maintenance;
	}

	public void setNicNumber(Integer nicNumber) {
		this.nicNumber = nicNumber;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setRamNumber(Integer ramNumber) {
		this.ramNumber = ramNumber;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
