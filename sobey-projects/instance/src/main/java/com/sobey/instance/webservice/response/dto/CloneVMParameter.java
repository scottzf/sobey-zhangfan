package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

@XmlRootElement(name = "CloneVMParameter")
@XmlType(name = "CloneVMParameter", namespace = WsConstants.NS)
public class CloneVMParameter {

	private String datacenter;
	private String description;
	private String gateway;
	private String ipaddress;
	private String subNetMask;
	private Integer vlanId;
	private String vMName;
	private String vMSUserName;
	private String vMTemplateName;
	private String vMTemplateOS;

	public String getDatacenter() {
		return datacenter;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getSubNetMask() {
		return subNetMask;
	}

	public void setSubNetMask(String subNetMask) {
		this.subNetMask = subNetMask;
	}

	public Integer getVlanId() {
		return vlanId;
	}

	public void setVlanId(Integer vlanId) {
		this.vlanId = vlanId;
	}

	public String getvMName() {
		return vMName;
	}

	public void setvMName(String vMName) {
		this.vMName = vMName;
	}

	public String getvMSUserName() {
		return vMSUserName;
	}

	public void setvMSUserName(String vMSUserName) {
		this.vMSUserName = vMSUserName;
	}

	public String getvMTemplateName() {
		return vMTemplateName;
	}

	public void setvMTemplateName(String vMTemplateName) {
		this.vMTemplateName = vMTemplateName;
	}

	public String getvMTemplateOS() {
		return vMTemplateOS;
	}

	public void setvMTemplateOS(String vMTemplateOS) {
		this.vMTemplateOS = vMTemplateOS;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
