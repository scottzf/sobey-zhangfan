package com.sobey.switches.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.switches.constans.WsConstants;

/**
 * 交换机上,执行脚本所需参数的Vlan对象:
 * 
 * <pre>
 * vlanId  	Vlan编号
 * netMask	子网掩码
 * gateway	网关
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "VlanParameter")
@XmlType(name = "VlanParameter", namespace = WsConstants.NS)
public class VlanParameter {

	/**
	 * Vlan编号
	 */
	private Integer vlanId;

	/**
	 * 子网掩码
	 */
	private String netMask;

	/**
	 * 网关
	 */
	private String gateway;

	public Integer getVlanId() {
		return vlanId;
	}

	public void setVlanId(Integer vlanId) {
		this.vlanId = vlanId;
	}

	public String getNetMask() {
		return netMask;
	}

	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
