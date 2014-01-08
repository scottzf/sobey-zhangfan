package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement(name = "NicPortDTO")
@XmlType(name = "NicPortDTO", namespace = WsConstants.NS)
public class NicPortDTO {

	private Integer id;
	private String code;
	private String description;
	private Date beginDate;
	private String remark;
	private Integer nic;
	private NicDTO nicDTO;
	private Integer connectedTo;
	private SwitchPortDTO switchPortDTO;
	private Integer ipaddress;
	private IpaddressDTO ipaddressDTO;
	private String site;
	private String macAddress;

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

	public Integer getNic() {
		return nic;
	}

	public void setNic(Integer nic) {
		this.nic = nic;
	}

	public NicDTO getNicDTO() {
		return nicDTO;
	}

	public void setNicDTO(NicDTO nicDTO) {
		this.nicDTO = nicDTO;
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

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}