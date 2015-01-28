package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "SubnetDTO")
@XmlType(name = "SubnetDTO", namespace = WsConstants.NS)
public class SubnetDTO extends BasicDTO {

	private Integer defaultSubnet;
	private String gateway;
	private Integer idc;
	private String netMask;
	private Integer portIndex;
	private String remark;
	private Integer router;
	private String segment;
	private Integer tenants;

	public Integer getDefaultSubnet() {
		return defaultSubnet;
	}

	public String getGateway() {
		return gateway;
	}

	public Integer getIdc() {
		return idc;
	}

	public String getNetMask() {
		return netMask;
	}

	public Integer getPortIndex() {
		return portIndex;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getRouter() {
		return router;
	}

	public String getSegment() {
		return segment;
	}

	public Integer getTenants() {
		return tenants;
	}

	public void setDefaultSubnet(Integer defaultSubnet) {
		this.defaultSubnet = defaultSubnet;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}

	public void setPortIndex(Integer portIndex) {
		this.portIndex = portIndex;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRouter(Integer router) {
		this.router = router;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}