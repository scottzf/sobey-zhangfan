package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "NetappPortDTO", namespace = WsConstants.NS)
public class NetappPortDTO {

	private Date beginDate;
	private String code;
	private String description;
	private Integer id;
	private String remark;

	private String site;
	private String macAddress;

	private Integer ipaddress;
	private Integer netappBox;
	private Integer connectedTo;

	private SwitchPortDTO switchPortDTO;
	private IpaddressDTO ipaddressDTO;
	private NetappBoxDTO netappBoxDTO;

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Integer getNetappBox() {
		return netappBox;
	}

	public void setNetappBox(Integer netappBox) {
		this.netappBox = netappBox;
	}

	public Integer getConnectedTo() {
		return connectedTo;
	}

	public void setConnectedTo(Integer connectedTo) {
		this.connectedTo = connectedTo;
	}

	public SwitchPortDTO getSwitchPortDTO() {
		return switchPortDTO;
	}

	public void setSwitchPortDTO(SwitchPortDTO switchPortDTO) {
		this.switchPortDTO = switchPortDTO;
	}

	public IpaddressDTO getIpaddressDTO() {
		return ipaddressDTO;
	}

	public void setIpaddressDTO(IpaddressDTO ipaddressDTO) {
		this.ipaddressDTO = ipaddressDTO;
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