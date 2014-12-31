package com.sobey.sdn.bean;

public class ECS {

	//基础配置
	private String ecsId;  //云主机ID
	
	private String ecsName;  //名称
	
	private String templateName;  //模板名称
	
	private String templateOS;  //模板操作系统
	
	private String type;  //类型
	
	private String specification;  //规格
	
	private String cpuNum;  //cpu数量
	
	private String memory;  //内存大小
	
	private String status;  //状态
	
	private String hostIp;  //主机IP
	//private String ;  //
	
	//网络配置
    private String localIp;  //内网IP
	
	private String eIp;  //公网IP
	
	private String subnetMask;  //子网掩码
	
	private String gateway;  //网关

	public String getEcsId() {
		return ecsId;
	}

	public void setEcsId(String ecsId) {
		this.ecsId = ecsId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getEcsName() {
		return ecsName;
	}

	public void setEcsName(String ecsName) {
		this.ecsName = ecsName;
	}

	public String getTemplateOS() {
		return templateOS;
	}

	public void setTemplateOS(String templateOS) {
		this.templateOS = templateOS;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSpecification() {
		return specification;
	}

	public String getSubnetMask() {
		return subnetMask;
	}

	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getCpuNum() {
		return cpuNum;
	}

	public void setCpuNum(String cpuNum) {
		this.cpuNum = cpuNum;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocalIp() {
		return localIp;
	}

	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	public String geteIp() {
		return eIp;
	}

	public void seteIp(String eIp) {
		this.eIp = eIp;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

}
