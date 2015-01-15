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
	private Integer idc;
	private Integer ipAddressPool;
	private Integer ipAddressStatus;
	private Integer isp;
	private String netMask;
	private String remark;
	private String segment;
	private Integer subnet;

	public String getGateway() {
		return gateway;
	}

	public Integer getIdc() {
		return idc;
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

	public String getNetMask() {
		return netMask;
	}

	public String getRemark() {
		return remark;
	}

	public String getSegment() {
		return segment;
	}

	public Integer getSubnet() {
		return subnet;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
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

	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public void setSubnet(Integer subnet) {
		this.subnet = subnet;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}