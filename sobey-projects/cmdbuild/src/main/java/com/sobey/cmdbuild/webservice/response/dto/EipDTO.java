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
	private Integer eipStatus;
	private Integer ipaddress;
	private Integer isp;
	private Integer router;

	public Integer getBandwidth() {
		return bandwidth;
	}

	public Integer getEipStatus() {
		return eipStatus;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public Integer getIsp() {
		return isp;
	}

	public Integer getRouter() {
		return router;
	}

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}

	public void setEipStatus(Integer eipStatus) {
		this.eipStatus = eipStatus;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setIsp(Integer isp) {
		this.isp = isp;
	}

	public void setRouter(Integer router) {
		this.router = router;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}