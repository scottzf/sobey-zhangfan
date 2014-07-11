package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "IpaddressDTO")
@XmlType(name = "IpaddressDTO", namespace = WsConstants.NS)
public class IpaddressDTO extends BasicDTO {

	private String gateway;
	private Integer ipAddressPool;
	private String ipAddressPoolText;
	private Integer ipAddressStatus;
	private String ipAddressStatusText;
	private Integer isp;
	private String ispText;
	private String netmask;
	private Integer vlan;
	private VlanDTO vlanDTO;

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public Integer getIpAddressPool() {
		return ipAddressPool;
	}

	public void setIpAddressPool(Integer ipAddressPool) {
		this.ipAddressPool = ipAddressPool;
	}

	public String getIpAddressPoolText() {
		return ipAddressPoolText;
	}

	public void setIpAddressPoolText(String ipAddressPoolText) {
		this.ipAddressPoolText = ipAddressPoolText;
	}

	public Integer getIpAddressStatus() {
		return ipAddressStatus;
	}

	public void setIpAddressStatus(Integer ipAddressStatus) {
		this.ipAddressStatus = ipAddressStatus;
	}

	public String getIpAddressStatusText() {
		return ipAddressStatusText;
	}

	public void setIpAddressStatusText(String ipAddressStatusText) {
		this.ipAddressStatusText = ipAddressStatusText;
	}

	public Integer getIsp() {
		return isp;
	}

	public void setIsp(Integer isp) {
		this.isp = isp;
	}

	public String getIspText() {
		return ispText;
	}

	public void setIspText(String ispText) {
		this.ispText = ispText;
	}

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public Integer getVlan() {
		return vlan;
	}

	public void setVlan(Integer vlan) {
		this.vlan = vlan;
	}

	public VlanDTO getVlanDTO() {
		return vlanDTO;
	}

	public void setVlanDTO(VlanDTO vlanDTO) {
		this.vlanDTO = vlanDTO;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}