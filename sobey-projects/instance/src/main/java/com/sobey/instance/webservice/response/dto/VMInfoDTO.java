package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

@XmlRootElement(name = "VMInfoDTO")
@XmlType(name = "VMInfoDTO", namespace = WsConstants.NS)
public class VMInfoDTO {

	/**
	 * CPU 数量
	 */
	private String cpuNumber;

	/**
	 * 数据中心
	 */
	private String datastore;

	/**
	 * 硬盘大小(GB)
	 */
	private String diskGB;

	/**
	 * 操作系统
	 */
	private String guestFullName;

	/**
	 * VM所属Host名称
	 */
	private String hostName;

	/**
	 * VM IP地址
	 */
	private String ipaddress;

	/**
	 * Mac 地址
	 */
	private String macIPaddress;

	/**
	 * 内存大小(MB)
	 */
	private String memoryMB;

	/**
	 * 虚拟机绑定端口组,多个用","分隔
	 */
	private String portGroups;

	/**
	 * VM运行状态
	 */
	private String status;

	/**
	 * VM名称
	 */
	private String vmName;

	public String getCpuNumber() {
		return cpuNumber;
	}

	public String getDatastore() {
		return datastore;
	}

	public String getDiskGB() {
		return diskGB;
	}

	public String getGuestFullName() {
		return guestFullName;
	}

	public String getHostName() {
		return hostName;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public String getMacIPaddress() {
		return macIPaddress;
	}

	public String getMemoryMB() {
		return memoryMB;
	}

	public String getPortGroups() {
		return portGroups;
	}

	public String getStatus() {
		return status;
	}

	public String getVmName() {
		return vmName;
	}

	public void setCpuNumber(String cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public void setDatastore(String datastore) {
		this.datastore = datastore;
	}

	public void setDiskGB(String diskGB) {
		this.diskGB = diskGB;
	}

	public void setGuestFullName(String guestFullName) {
		this.guestFullName = guestFullName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setMacIPaddress(String macIPaddress) {
		this.macIPaddress = macIPaddress;
	}

	public void setMemoryMB(String memoryMB) {
		this.memoryMB = memoryMB;
	}

	public void setPortGroups(String portGroups) {
		this.portGroups = portGroups;
	}

	public void setStatus(String status) {
		this.status = status;
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