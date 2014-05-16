package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

@XmlRootElement(name = "DestroyVMParameter")
@XmlType(name = "DestroyVMParameter", namespace = WsConstants.NS)
public class DestroyVMParameter {

	private String vMName;
	private String datacenter;

	public String getvMName() {
		return vMName;
	}

	public void setvMName(String vMName) {
		this.vMName = vMName;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
