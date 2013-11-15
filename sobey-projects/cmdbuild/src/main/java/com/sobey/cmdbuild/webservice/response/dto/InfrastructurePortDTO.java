package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sobey.cmdbuild.constants.DevicePortTypeEnum;
import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "InfrastructurePortDTO", namespace = WsConstants.NS)
public class InfrastructurePortDTO {

	private Date beginDate;
	private String code;
	private Integer connectedTo;
	private String description;
	private DevicePortTypeEnum devicePortType;
	private Integer id;
	private Integer infrastructure;
	private Integer ipAddress;
	private String macAddress;
	private String remark;
	private String site;

	public Date getBeginDate() {
		return beginDate;
	}

	public String getCode() {
		return code;
	}

	public Integer getConnectedTo() {
		return connectedTo;
	}

	public String getDescription() {
		return description;
	}

	public DevicePortTypeEnum getDevicePortType() {
		return devicePortType;
	}

	public Integer getId() {
		return id;
	}

	public Integer getInfrastructure() {
		return infrastructure;
	}

	public Integer getIpAddress() {
		return ipAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public String getRemark() {
		return remark;
	}

	public String getSite() {
		return site;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setConnectedTo(Integer connectedTo) {
		this.connectedTo = connectedTo;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDevicePortType(DevicePortTypeEnum devicePortType) {
		this.devicePortType = devicePortType;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setInfrastructure(Integer infrastructure) {
		this.infrastructure = infrastructure;
	}

	public void setIpAddress(Integer ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSite(String site) {
		this.site = site;
	}

}
