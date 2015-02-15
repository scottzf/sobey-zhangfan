package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

/**
 * Run VM的参数对象
 * 
 * @author Administrator
 *
 */
@XmlRootElement(name = "RunVMParameter")
@XmlType(name = "RunVMParameter", namespace = WsConstants.NS)
public class RunVMParameter {

	/**
	 * CPU数量
	 */
	private Integer cpuNumber;

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
	 * 内存大小(MB)
	 */
	private Integer memoryMB;

	/**
	 * 子网掩码
	 */
	private String subNetMask;

	/**
	 * vCenter中临时VM的名称
	 * 
	 */
	private String tempVMName;

	/**
	 * VM名称
	 */
	private String vmName;
	/**
	 * 模板操作系统类型:linux or windows
	 */
	private String vmTemplateOS;

	public RunVMParameter() {
	}

	public RunVMParameter(Integer cpuNumber, String datacenter, String description, String gateway, String ipaddress,
			Integer memoryMB, String subNetMask, String tempVMName, String vmName, String vmTemplateOS) {
		super();
		this.cpuNumber = cpuNumber;
		this.datacenter = datacenter;
		this.description = description;
		this.gateway = gateway;
		this.ipaddress = ipaddress;
		this.memoryMB = memoryMB;
		this.subNetMask = subNetMask;
		this.tempVMName = tempVMName;
		this.vmName = vmName;
		this.vmTemplateOS = vmTemplateOS;
	}

	public Integer getCpuNumber() {
		return cpuNumber;
	}

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

	public Integer getMemoryMB() {
		return memoryMB;
	}

	public String getSubNetMask() {
		return subNetMask;
	}

	public String getTempVMName() {
		return tempVMName;
	}

	public String getVmName() {
		return vmName;
	}

	public String getVmTemplateOS() {
		return vmTemplateOS;
	}

	public void setCpuNumber(Integer cpuNumber) {
		this.cpuNumber = cpuNumber;
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

	public void setMemoryMB(Integer memoryMB) {
		this.memoryMB = memoryMB;
	}

	public void setSubNetMask(String subNetMask) {
		this.subNetMask = subNetMask;
	}

	public void setTempVMName(String tempVMName) {
		this.tempVMName = tempVMName;
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
