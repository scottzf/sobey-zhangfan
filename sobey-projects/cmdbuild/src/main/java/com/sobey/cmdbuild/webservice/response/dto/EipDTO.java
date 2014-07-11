package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ServiceBasicDTO;

@XmlRootElement(name = "EipDTO")
@XmlType(name = "EipDTO", namespace = WsConstants.NS)
public class EipDTO extends ServiceBasicDTO {

	private Integer bandwidth;
	private String bandwidthText;
	private Integer eipStatus;
	private String eipStatusText;
	private Integer ipaddress;
	private IpaddressDTO ipaddressDTO;
	private Integer isp;
	private String ispText;

	public Integer getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getBandwidthText() {
		return bandwidthText;
	}

	public void setBandwidthText(String bandwidthText) {
		this.bandwidthText = bandwidthText;
	}

	public Integer getEipStatus() {
		return eipStatus;
	}

	public void setEipStatus(Integer eipStatus) {
		this.eipStatus = eipStatus;
	}

	public String getEipStatusText() {
		return eipStatusText;
	}

	public void setEipStatusText(String eipStatusText) {
		this.eipStatusText = eipStatusText;
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

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}