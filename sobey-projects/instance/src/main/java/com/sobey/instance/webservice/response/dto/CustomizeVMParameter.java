package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

/**
 * 自定义VM的参数对象
 * 
 * @author Administrator
 *
 */
@XmlRootElement(name = "CustomizeVMParameter")
@XmlType(name = "CustomizeVMParameter", namespace = WsConstants.NS)
public class CustomizeVMParameter {

	/**
	 * 数据中心
	 */
	private String datacenter;

	/**
	 * 网关
	 */
	private String gateway;

	/**
	 * VM IP
	 */
	private String ipaddress;

	/**
	 * 子网掩码
	 */
	private String subNetMask;

	/**
	 * VM名称
	 */
	private String vmName;

	/**
	 * 模板操作系统类型:linux or windows
	 */
	private String vmTemplateOS;

	public CustomizeVMParameter() {
	}

	public CustomizeVMParameter(String datacenter, String gateway, String ipaddress, String subNetMask, String vmName,
			String vmTemplateOS) {
		super();
		this.datacenter = datacenter;
		this.gateway = gateway;
		this.ipaddress = ipaddress;
		this.subNetMask = subNetMask;
		this.vmName = vmName;
		this.vmTemplateOS = vmTemplateOS;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public String getGateway() {
		return gateway;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public String getSubNetMask() {
		return subNetMask;
	}

	public String getVmName() {
		return vmName;
	}

	public String getVmTemplateOS() {
		return vmTemplateOS;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setSubNetMask(String subNetMask) {
		this.subNetMask = subNetMask;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public void setVmTemplateOS(String vmTemplateOS) {
		this.vmTemplateOS = vmTemplateOS;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
