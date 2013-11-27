package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "IpaddressDTO", namespace = WsConstants.NS)
public class IpaddressDTO {

	private Integer id;
	private String code;
	private String description;
	private Date beginDate;
	private String remark;
	private String gateway;
	private String netmask;

	private Integer vlan;
	private VlanDTO vlanDTO;

	private Integer isp;
	private Integer ipAddressPool;
	private Integer ipAddressStatus;

	private String ispText;
	private String ipAddressPoolText;
	private String ipAddressStatusText;

	public VlanDTO getVlanDTO() {
		return vlanDTO;
	}

	public void setVlanDTO(VlanDTO vlanDTO) {
		this.vlanDTO = vlanDTO;
	}

	public String getIspText() {
		return ispText;
	}

	public void setIspText(String ispText) {
		this.ispText = ispText;
	}

	public String getIpAddressPoolText() {
		return ipAddressPoolText;
	}

	public void setIpAddressPoolText(String ipAddressPoolText) {
		this.ipAddressPoolText = ipAddressPoolText;
	}

	public String getIpAddressStatusText() {
		return ipAddressStatusText;
	}

	public void setIpAddressStatusText(String ipAddressStatusText) {
		this.ipAddressStatusText = ipAddressStatusText;
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

	public String getGateway() {
		return gateway;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIpAddressPool() {
		return ipAddressPool;
	}

	public Integer getIpAddressStatus() {
		return ipAddressStatus;
	}

	public Integer getIsp() {
		return isp;
	}

	public String getNetmask() {
		return netmask;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getVlan() {
		return vlan;
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

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIpAddressPool(Integer ipAddressPool) {
		this.ipAddressPool = ipAddressPool;
	}

	public void setIpAddressStatus(Integer ipAddressStatus) {
		this.ipAddressStatus = ipAddressStatus;
	}

	public void setIsp(Integer isp) {
		this.isp = isp;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setVlan(Integer vlan) {
		this.vlan = vlan;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}