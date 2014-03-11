package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

@XmlRootElement(name = "ReconfigVMParameter")
@XmlType(name = "ReconfigVMParameter", namespace = WsConstants.NS)
public class ReconfigVMParameter {

	private String vMName;
	private Long memoryMB;
	private Integer cPUNumber;

	public String getvMName() {
		return vMName;
	}

	public void setvMName(String vMName) {
		this.vMName = vMName;
	}

	public Long getMemoryMB() {
		return memoryMB;
	}

	public void setMemoryMB(Long memoryMB) {
		this.memoryMB = memoryMB;
	}

	public Integer getcPUNumber() {
		return cPUNumber;
	}

	public void setcPUNumber(Integer cPUNumber) {
		this.cPUNumber = cPUNumber;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
