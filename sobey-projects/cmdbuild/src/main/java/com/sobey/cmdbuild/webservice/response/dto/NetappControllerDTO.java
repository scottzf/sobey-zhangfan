package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "NetappControllerDTO", namespace = WsConstants.NS)
public class NetappControllerDTO {

	private Date beginDate;
	private String code;
	private String description;
	private Integer id;
	private String remark;

	private String site;
	private String gdzcSn;
	private String sn;

	private Integer idc;
	private Integer ipaddress;
	private Integer rack;
	private Integer deviceSpec;
	private Integer netappBox;

	private IdcDTO idcDTO;
	private RackDTO rackDTO;
	private IpaddressDTO ipaddressDTO;
	private DeviceSpecDTO deviceSpecDTO;
	private NetappBoxDTO netappBoxDTO;

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getGdzcSn() {
		return gdzcSn;
	}

	public void setGdzcSn(String gdzcSn) {
		this.gdzcSn = gdzcSn;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getIdc() {
		return idc;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Integer getRack() {
		return rack;
	}

	public void setRack(Integer rack) {
		this.rack = rack;
	}

	public Integer getDeviceSpec() {
		return deviceSpec;
	}

	public void setDeviceSpec(Integer deviceSpec) {
		this.deviceSpec = deviceSpec;
	}

	public Integer getNetappBox() {
		return netappBox;
	}

	public void setNetappBox(Integer netappBox) {
		this.netappBox = netappBox;
	}

	public IdcDTO getIdcDTO() {
		return idcDTO;
	}

	public void setIdcDTO(IdcDTO idcDTO) {
		this.idcDTO = idcDTO;
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

	public NetappBoxDTO getNetappBoxDTO() {
		return netappBoxDTO;
	}

	public void setNetappBoxDTO(NetappBoxDTO netappBoxDTO) {
		this.netappBoxDTO = netappBoxDTO;
	}

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

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}