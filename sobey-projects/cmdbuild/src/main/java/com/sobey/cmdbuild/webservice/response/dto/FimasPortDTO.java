package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "FimasPortDTO", namespace = WsConstants.NS)
public class FimasPortDTO {

	private Date beginDate;
	private String code;
	private String description;
	private Integer id;
	private String remark;

	private FimasDTO fimasDTO;
	private IpaddressDTO ipaddressDTO;
	private Integer connectedTo;
	private SwitchPortDTO switchPortDTO;
	private Integer fimas;
	private String macAddress;
	private String site;
	private Integer ipaddress;

	public FimasDTO getFimasDTO() {
		return fimasDTO;
	}

	public void setFimasDTO(FimasDTO fimasDTO) {
		this.fimasDTO = fimasDTO;
	}

	public IpaddressDTO getIpaddressDTO() {
		return ipaddressDTO;
	}

	public void setIpaddressDTO(IpaddressDTO ipaddressDTO) {
		this.ipaddressDTO = ipaddressDTO;
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

	public Integer getFimas() {
		return fimas;
	}

	public void setFimas(Integer fimas) {
		this.fimas = fimas;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
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