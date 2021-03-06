package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

/**
 * 销毁VM的参数对象
 * 
 * @author Administrator
 *
 */
@XmlRootElement(name = "DestroyVMParameter")
@XmlType(name = "DestroyVMParameter", namespace = WsConstants.NS)
public class DestroyVMParameter {

	/**
	 * 数据中心
	 */
	private String datacenter;

	/**
	 * VM名称
	 */
	private String vmName;

	public String getDatacenter() {
		return datacenter;
	}

	public String getVmName() {
		return vmName;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
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
