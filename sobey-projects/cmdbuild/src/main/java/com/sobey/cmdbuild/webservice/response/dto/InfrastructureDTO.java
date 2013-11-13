package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sobey.cmdbuild.constants.DeviceTypeEnum;
import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "InfrastructureDTO", namespace = WsConstants.NS)
public class InfrastructureDTO {

	private Date beginDate;
	private String code;
	private String description;
	private Integer deviceSpec;
	private Integer deviceStatus;
	private DeviceTypeEnum deviceType;
	private Integer fimasBox;
	private String gdzcSn;
	private Integer id;
	private Integer idc;
	private Integer ipAddress;
	private Integer netAppBox;
	private Integer rack;
	private String remark;
	private String site;
	private String sn;

	public Date getBeginDate() {
		return beginDate;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Integer getDeviceSpec() {
		return deviceSpec;
	}

	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public DeviceTypeEnum getDeviceType() {
		return deviceType;
	}

	public Integer getFimasBox() {
		return fimasBox;
	}

	public String getGdzcSn() {
		return gdzcSn;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIdc() {
		return idc;
	}

	public Integer getIpAddress() {
		return ipAddress;
	}

	public Integer getNetAppBox() {
		return netAppBox;
	}

	public Integer getRack() {
		return rack;
	}

	public String getRemark() {
		return remark;
	}

	public String getSite() {
		return site;
	}

	public String getSn() {
		return sn;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDeviceSpec(Integer deviceSpec) {
		this.deviceSpec = deviceSpec;
	}

	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public void setDeviceType(DeviceTypeEnum deviceType) {
		this.deviceType = deviceType;
	}

	public void setFimasBox(Integer fimasBox) {
		this.fimasBox = fimasBox;
	}

	public void setGdzcSn(String gdzcSn) {
		this.gdzcSn = gdzcSn;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setIpAddress(Integer ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setNetAppBox(Integer netAppBox) {
		this.netAppBox = netAppBox;
	}

	public void setRack(Integer rack) {
		this.rack = rack;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

}
