package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ServiceBasicDTO;

@XmlRootElement(name = "EcsDTO")
@XmlType(name = "EcsDTO", namespace = WsConstants.NS)
public class EcsDTO extends ServiceBasicDTO {

	private String adapterName;
	private String cpuNumber;
	private String datastoreName;
	private String diskSize;
	private Integer ecsSpec;
	private Integer ecsStatus;
	private Integer ecsType;
	private Integer ipaddress;
	private Boolean isDesktop;
	private Boolean isGpu;
	private String macAddress;
	private String memorySize;
	private String osName;
	private Integer server;
	private Integer subnet;

	public String getAdapterName() {
		return adapterName;
	}

	public String getCpuNumber() {
		return cpuNumber;
	}

	public String getDatastoreName() {
		return datastoreName;
	}

	public String getDiskSize() {
		return diskSize;
	}

	public Integer getEcsSpec() {
		return ecsSpec;
	}

	public Integer getEcsStatus() {
		return ecsStatus;
	}

	public Integer getEcsType() {
		return ecsType;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public Boolean getIsDesktop() {
		return isDesktop;
	}

	public Boolean getIsGpu() {
		return isGpu;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public String getMemorySize() {
		return memorySize;
	}

	public String getOsName() {
		return osName;
	}

	public Integer getServer() {
		return server;
	}

	public Integer getSubnet() {
		return subnet;
	}

	public void setAdapterName(String adapterName) {
		this.adapterName = adapterName;
	}

	public void setCpuNumber(String cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public void setDatastoreName(String datastoreName) {
		this.datastoreName = datastoreName;
	}

	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}

	public void setEcsSpec(Integer ecsSpec) {
		this.ecsSpec = ecsSpec;
	}

	public void setEcsStatus(Integer ecsStatus) {
		this.ecsStatus = ecsStatus;
	}

	public void setEcsType(Integer ecsType) {
		this.ecsType = ecsType;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setIsDesktop(Boolean isDesktop) {
		this.isDesktop = isDesktop;
	}

	public void setIsGpu(Boolean isGpu) {
		this.isGpu = isGpu;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public void setMemorySize(String memorySize) {
		this.memorySize = memorySize;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public void setServer(Integer server) {
		this.server = server;
	}

	public void setSubnet(Integer subnet) {
		this.subnet = subnet;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}