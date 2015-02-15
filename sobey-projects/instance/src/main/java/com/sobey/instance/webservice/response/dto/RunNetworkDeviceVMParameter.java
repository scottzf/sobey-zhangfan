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
@XmlRootElement(name = "RunNetworkDeviceVMParameter")
@XmlType(name = "RunNetworkDeviceVMParameter", namespace = WsConstants.NS)
public class RunNetworkDeviceVMParameter {

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
	 * 内存大小(MB)
	 */
	private Integer memoryMB;

	/**
	 * vCenter中临时VM的名称
	 * 
	 */
	private String tempVMName;

	/**
	 * VM名称
	 */
	private String vmName;

	public RunNetworkDeviceVMParameter() {
	}

	public RunNetworkDeviceVMParameter(Integer cpuNumber, String datacenter, String description, Integer memoryMB,
			String tempVMName, String vmName) {
		super();
		this.cpuNumber = cpuNumber;
		this.datacenter = datacenter;
		this.description = description;
		this.memoryMB = memoryMB;
		this.tempVMName = tempVMName;
		this.vmName = vmName;
	}

	public Integer getCpuNumber() {
		return cpuNumber;
	}

	public void setCpuNumber(Integer cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

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

	public Integer getMemoryMB() {
		return memoryMB;
	}

	public void setMemoryMB(Integer memoryMB) {
		this.memoryMB = memoryMB;
	}

	public String getTempVMName() {
		return tempVMName;
	}

	public void setTempVMName(String tempVMName) {
		this.tempVMName = tempVMName;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
