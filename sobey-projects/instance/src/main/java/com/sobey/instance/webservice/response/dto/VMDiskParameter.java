package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

@XmlRootElement(name = "VMDiskParameter")
@XmlType(name = "VMDiskParameter", namespace = WsConstants.NS)
public class VMDiskParameter {

	/**
	 * 数据中心
	 */
	private String datacenter;

	/**
	 * 存储器大小(GB)
	 */
	private String diskGB;

	/**
	 * 存储器名称
	 */
	private String diskName;

	/**
	 * VM名称
	 */
	private String vmName;

	public String getDatacenter() {
		return datacenter;
	}

	public String getDiskGB() {
		return diskGB;
	}

	public String getDiskName() {
		return diskName;
	}

	public String getVmName() {
		return vmName;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	public void setDiskGB(String diskGB) {
		this.diskGB = diskGB;
	}

	public void setDiskName(String diskName) {
		this.diskName = diskName;
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
