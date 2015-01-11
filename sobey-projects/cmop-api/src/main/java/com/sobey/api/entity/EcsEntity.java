package com.sobey.api.entity;

public class EcsEntity {

	private String cpuNumber;
	private String ecsName;
	private String idc;
	private String identifier;
	private String ipaddress;
	private Boolean isDesktop;
	private Boolean isGpu;
	private String memorySize;
	private String remark;
	private String specification;
	private String status;

	public EcsEntity(String cpuNumber, String ecsName, String idc, String identifier, String ipaddress,
			Boolean isDesktop, Boolean isGpu, String memorySize, String remark, String specification, String status) {
		super();
		this.cpuNumber = cpuNumber;
		this.ecsName = ecsName;
		this.idc = idc;
		this.identifier = identifier;
		this.ipaddress = ipaddress;
		this.isDesktop = isDesktop;
		this.isGpu = isGpu;
		this.memorySize = memorySize;
		this.remark = remark;
		this.specification = specification;
		this.status = status;
	}

	public String getCpuNumber() {
		return cpuNumber;
	}

	public String getEcsName() {
		return ecsName;
	}

	public String getIdc() {
		return idc;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public Boolean getIsDesktop() {
		return isDesktop;
	}

	public Boolean getIsGpu() {
		return isGpu;
	}

	public String getMemorySize() {
		return memorySize;
	}

	public String getRemark() {
		return remark;
	}

	public String getSpecification() {
		return specification;
	}

	public String getStatus() {
		return status;
	}

	public void setCpuNumber(String cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public void setEcsName(String ecsName) {
		this.ecsName = ecsName;
	}

	public void setIdc(String idc) {
		this.idc = idc;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setIsDesktop(Boolean isDesktop) {
		this.isDesktop = isDesktop;
	}

	public void setIsGpu(Boolean isGpu) {
		this.isGpu = isGpu;
	}

	public void setMemorySize(String memorySize) {
		this.memorySize = memorySize;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
