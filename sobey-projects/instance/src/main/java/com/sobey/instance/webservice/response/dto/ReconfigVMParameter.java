package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

/**
 * 修改VM配置的参数对象
 * 
 * @author Administrator
 *
 */
@XmlRootElement(name = "ReconfigVMParameter")
@XmlType(name = "ReconfigVMParameter", namespace = WsConstants.NS)
public class ReconfigVMParameter {

	/**
	 * CPU数量
	 */
	private Integer cpuNumber;

	/**
	 * 数据中心
	 */
	private String datacenter;

	/**
	 * 内存大小(MB)
	 */
	private Integer memoryMB;

	/**
	 * VM名称
	 */
	private String vmName;

	public ReconfigVMParameter() {
	}

	public ReconfigVMParameter(Integer cpuNumber, String datacenter, Integer memoryMB, String vmName) {
		super();
		this.cpuNumber = cpuNumber;
		this.datacenter = datacenter;
		this.memoryMB = memoryMB;
		this.vmName = vmName;
	}

	public Integer getCpuNumber() {
		return cpuNumber;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public Integer getMemoryMB() {
		return memoryMB;
	}

	public String getVmName() {
		return vmName;
	}

	public void setCpuNumber(Integer cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	public void setMemoryMB(Integer memoryMB) {
		this.memoryMB = memoryMB;
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
