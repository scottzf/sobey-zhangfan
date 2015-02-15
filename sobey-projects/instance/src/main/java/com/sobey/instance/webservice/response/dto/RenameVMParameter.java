package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

/**
 * 修改VM名称的参数对象
 * 
 * @author Administrator
 *
 */
@XmlRootElement(name = "RenameVMParameter")
@XmlType(name = "RenameVMParameter", namespace = WsConstants.NS)
public class RenameVMParameter {

	/**
	 * 数据中心
	 */
	private String datacenter;

	/**
	 * vCenter中临时VM的名称
	 * 
	 */
	private String tempVMName;

	/**
	 * VM名称
	 */
	private String vmName;

	public RenameVMParameter() {
	}

	public RenameVMParameter(String datacenter, String tempVMName, String vmName) {
		super();
		this.datacenter = datacenter;
		this.tempVMName = tempVMName;
		this.vmName = vmName;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public String getTempVMName() {
		return tempVMName;
	}

	public String getVmName() {
		return vmName;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	public void setTempVMName(String tempVMName) {
		this.tempVMName = tempVMName;
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
