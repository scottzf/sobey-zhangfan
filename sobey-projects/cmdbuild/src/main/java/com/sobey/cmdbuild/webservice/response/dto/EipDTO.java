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
	private Integer portId;
	private Integer router;
	private RouterDTO routerDTO;

	
	public Integer getBandwidth() {
		return bandwidth;
	}

	public String getBandwidthText() {
		return bandwidthText;
	}

	public Integer getEipStatus() {
		return eipStatus;
	}

	public String getEipStatusText() {
		return eipStatusText;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public IpaddressDTO getIpaddressDTO() {
		return ipaddressDTO;
	}

	public Integer getIsp() {
		return isp;
	}

	public String getIspText() {
		return ispText;
	}

	public Integer getPortId() {
		return portId;
	}

	public Integer getRouter() {
		return router;
	}

	public RouterDTO getRouterDTO() {
		return routerDTO;
	}

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}

	public void setBandwidthText(String bandwidthText) {
		this.bandwidthText = bandwidthText;
	}

	public void setEipStatus(Integer eipStatus) {
		this.eipStatus = eipStatus;
	}

	public void setEipStatusText(String eipStatusText) {
		this.eipStatusText = eipStatusText;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setIpaddressDTO(IpaddressDTO ipaddressDTO) {
		this.ipaddressDTO = ipaddressDTO;
	}

	public void setIsp(Integer isp) {
		this.isp = isp;
	}

	public void setIspText(String ispText) {
		this.ispText = ispText;
	}

	public void setPortId(Integer portId) {
		this.portId = portId;
	}

	public void setRouter(Integer router) {
		this.router = router;
	}

	public void setRouterDTO(RouterDTO routerDTO) {
		this.routerDTO = routerDTO;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}