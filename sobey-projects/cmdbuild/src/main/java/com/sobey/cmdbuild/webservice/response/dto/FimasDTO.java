package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;
import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "FimasDTO", namespace = WsConstants.NS)
public class FimasDTO {

	private Integer id;
	private String code;
	private String description;
	private Date beginDate;
	private String remark;

	private String sn;
	private String gdzcsn;
	private String site;

	private Integer idc;
	private Integer rack;
	private Integer ipaddress;
	private Integer deviceSpec;
	private Integer fimasBox;

	private IdcDTO idcDTO;
	private RackDTO rackDTO;
	private IpaddressDTO ipaddressDTO;
	private DeviceSpecDTO deviceSpecDTO;
	private FimasBoxDTO fimasBoxDTO;

	public Date getBeginDate() {
		return beginDate;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Integer getId() {
		return id;
	}

	public String getRemark() {
		return remark;
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

	public void setId(Integer id) {
		this.id = id;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getGdzcsn() {
		return gdzcsn;
	}

	public void setGdzcsn(String gdzcsn) {
		this.gdzcsn = gdzcsn;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Integer getIdc() {
		return idc;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public Integer getRack() {
		return rack;
	}

	public void setRack(Integer rack) {
		this.rack = rack;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Integer getDeviceSpec() {
		return deviceSpec;
	}

	public void setDeviceSpec(Integer deviceSpec) {
		this.deviceSpec = deviceSpec;
	}

	public Integer getFimasBox() {
		return fimasBox;
	}

	public void setFimasBox(Integer fimasBox) {
		this.fimasBox = fimasBox;
	}

	public RackDTO getRackDTO() {
		return rackDTO;
	}

	public void setRackDTO(RackDTO rackDTO) {
		this.rackDTO = rackDTO;
	}

	public IpaddressDTO getIpaddressDTO() {
		return ipaddressDTO;
	}

	public void setIpaddressDTO(IpaddressDTO ipaddressDTO) {
		this.ipaddressDTO = ipaddressDTO;
	}

	public DeviceSpecDTO getDeviceSpecDTO() {
		return deviceSpecDTO;
	}

	public void setDeviceSpecDTO(DeviceSpecDTO deviceSpecDTO) {
		this.deviceSpecDTO = deviceSpecDTO;
	}

	public FimasBoxDTO getFimasBoxDTO() {
		return fimasBoxDTO;
	}

	public void setFimasBoxDTO(FimasBoxDTO fimasBoxDTO) {
		this.fimasBoxDTO = fimasBoxDTO;
	}

	public IdcDTO getIdcDTO() {
		return idcDTO;
	}

	public void setIdcDTO(IdcDTO idcDTO) {
		this.idcDTO = idcDTO;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}