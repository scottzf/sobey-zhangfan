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
	 * 宿主机在vCenter中的Id
	 */
	private String hostId;

	/**
	 * 宿主机在vCenter中,resourcePool的Id
	 */
	private String resourcePool;

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

	public CloneVMParameter() {
	}

	public CloneVMParameter(String datacenter, String description, String hostId, String resourcePool, String vmName,
			String vmTemplateName, String vmTemplateOS) {
		super();
		this.datacenter = datacenter;
		this.description = description;
		this.hostId = hostId;
		this.resourcePool = resourcePool;
		this.vmName = vmName;
		this.vmTemplateName = vmTemplateName;
		this.vmTemplateOS = vmTemplateOS;
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

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getResourcePool() {
		return resourcePool;
	}

	public void setResourcePool(String resourcePool) {
		this.resourcePool = resourcePool;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getVmTemplateName() {
		return vmTemplateName;
	}

	public void setVmTemplateName(String vmTemplateName) {
		this.vmTemplateName = vmTemplateName;
	}

	public String getVmTemplateOS() {
		return vmTemplateOS;
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
