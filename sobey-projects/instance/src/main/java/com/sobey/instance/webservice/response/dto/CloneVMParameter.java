package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

/**
 * 克隆VM的参数对象
 * 
 * @author Administrator
 *
 */
@XmlRootElement(name = "CloneVMParameter")
@XmlType(name = "CloneVMParameter", namespace = WsConstants.NS)
public class CloneVMParameter {

	/**
	 * 数据中心
	 */
	private String datacenter;

	/**
	 * 说明
	 */
	private String description;

	/**
	 * 网关
	 */
	private String gateway;

	/**
	 * VM IP
	 */
	private String ipaddress;

	/**
	 * 宿主机在vCenter中,resourcePool的Id
	 */
	private String resourcePool;

	/**
	 * 子网掩码
	 */
	private String subNetMask;

	/**
	 * VM名称
	 */
	private String vmName;

	/**
	 * 所克隆的模板名称
	 */
	private String vmTemplateName;

	/**
	 * 模板操作系统类型:linux or windows
	 */
	private String vmTemplateOS;

	public String getDatacenter() {
		return datacenter;
	}

	public String getDescription() {
		return description;
	}

	public String getGateway() {
		return gateway;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public String getResourcePool() {
		return resourcePool;
	}

	public String getSubNetMask() {
		return subNetMask;
	}

	public String getVmName() {
		return vmName;
	}

	public String getVmTemplateName() {
		return vmTemplateName;
	}

	public String getVmTemplateOS() {
		return vmTemplateOS;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setResourcePool(String resourcePool) {
		this.resourcePool = resourcePool;
	}

	public void setSubNetMask(String subNetMask) {
		this.subNetMask = subNetMask;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public void setVmTemplateName(String vmTemplateName) {
		this.vmTemplateName = vmTemplateName;
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
