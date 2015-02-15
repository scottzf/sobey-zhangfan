package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

/**
 * VM电源操作的参数对象
 * 
 * @author Administrator
 *
 */
@XmlRootElement(name = "PowerVMParameter")
@XmlType(name = "PowerVMParameter", namespace = WsConstants.NS)
public class PowerVMParameter {

	/**
	 * 数据中心
	 */
	private String datacenter;

	/**
	 * 电源操作 eg:poweroff, poweron
	 */
	private String powerOperation;

	/**
	 * VM名称
	 */
	private String vmName;

	public PowerVMParameter() {
	}

	public PowerVMParameter(String datacenter, String powerOperation, String vmName) {
		super();
		this.datacenter = datacenter;
		this.powerOperation = powerOperation;
		this.vmName = vmName;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public String getPowerOperation() {
		return powerOperation;
	}

	public String getVmName() {
		return vmName;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	public void setPowerOperation(String powerOperation) {
		this.powerOperation = powerOperation;
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
