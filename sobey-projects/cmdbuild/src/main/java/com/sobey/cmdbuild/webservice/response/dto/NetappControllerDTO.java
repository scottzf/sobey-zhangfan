package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement(name = "NetappControllerDTO")
@XmlType(name = "NetappControllerDTO", namespace = WsConstants.NS)
public class NetappControllerDTO {

	private Integer id;
	private String code;
	private String description;
	private Date beginDate;
	private String remark;
	private Integer idc;
	private IdcDTO idcDTO;
	private Integer rack;
	private RackDTO rackDTO;
	private Integer deviceSpec;
	private DeviceSpecDTO deviceSpecDTO;
	private Integer ipaddress;
	private IpaddressDTO ipaddressDTO;
	private String site;
	private String sn;
	private String gdzcSn;

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

	public Integer getIdc() {
		return idc;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public IdcDTO getIdcDTO() {
		return idcDTO;
	}

	public void setIdcDTO(IdcDTO idcDTO) {
		this.idcDTO = idcDTO;
	}

	public Integer getRack() {
		return rack;
	}

	public void setRack(Integer rack) {
		this.rack = rack;
	}

	public RackDTO getRackDTO() {
		return rackDTO;
	}

	public void setRackDTO(RackDTO rackDTO) {
		this.rackDTO = rackDTO;
	}

	public Integer getDeviceSpec() {
		return deviceSpec;
	}

	public void setDeviceSpec(Integer deviceSpec) {
		this.deviceSpec = deviceSpec;
	}

	public DeviceSpecDTO getDeviceSpecDTO() {
		return deviceSpecDTO;
	}

	public void setDeviceSpecDTO(DeviceSpecDTO deviceSpecDTO) {
		this.deviceSpecDTO = deviceSpecDTO;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public IpaddressDTO getIpaddressDTO() {
		return ipaddressDTO;
	}

	public void setIpaddressDTO(IpaddressDTO ipaddressDTO) {
		this.ipaddressDTO = ipaddressDTO;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getGdzcSn() {
		return gdzcSn;
	}

	public void setGdzcSn(String gdzcSn) {
		this.gdzcSn = gdzcSn;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}