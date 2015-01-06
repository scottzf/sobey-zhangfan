package com.sobey.firewall.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.firewall.constans.WsConstants;

/**
 * 防火墙上,绑定路由时Subnet对象
 * 
 * @author Administrator
 */
@XmlRootElement(name = "SubnetParameter")
@XmlType(name = "SubnetParameter", namespace = WsConstants.NS)
public class SubnetParameter {

	/**
	 * 网关
	 */
	private String gateway;

	/**
	 * 和子网绑定的策略Id,策略Id生成策略应该是递增.
	 */
	private Integer policyId;

	/**
	 * 防火墙中的接口名
	 */
	private String portName;

	/**
	 * 网段
	 */
	private String segment;

	/**
	 * 子网掩码
	 */
	private String subnetMask;

	public String getGateway() {
		return gateway;
	}

	public Integer getPolicyId() {
		return policyId;
	}

	public String getPortName() {
		return portName;
	}

	public String getSegment() {
		return segment;
	}

	public String getSubnetMask() {
		return subnetMask;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
