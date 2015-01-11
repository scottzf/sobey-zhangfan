package com.sobey.sdn.test.testParameter;

public class CreateECSParameter {
	
	private String templateName;  //模板名
	
	private String templateOS;    //模板客户机系统
	
	private String vmName;   //虚拟机名称
	
	private String localIp;   //内网IP
	
	private String gateway;    //内网网关
	
	private String subNetMask;  //内网掩码
	
	private String tenantId;    //租户ID
	
	private String hostIp;      //主机IP
	
	private int vlanId;

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateOS() {
		return templateOS;
	}

	public void setTemplateOS(String templateOS) {
		this.templateOS = templateOS;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getLocalIp() {
		return localIp;
	}

	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getSubNetMask() {
		return subNetMask;
	}

	public void setSubNetMask(String subNetMask) {
		this.subNetMask = subNetMask;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public int getVlanId() {
		return vlanId;
	}

	public void setVlanId(int vlanId) {
		this.vlanId = vlanId;
	}    
	
	
}
