package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement(name = "TagRelation")
@XmlType(name = "TagRelation", namespace = WsConstants.NS)
public class TagRelation {

	private String parentTagName;
	private String serviceCode;
	private String serviceName;
	private String tagName;
	private String tagTypeName;

	public String getParentTagName() {
		return parentTagName;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getTagName() {
		return tagName;
	}

	public String getTagTypeName() {
		return tagTypeName;
	}

	public void setParentTagName(String parentTagName) {
		this.parentTagName = parentTagName;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public void setTagTypeName(String tagTypeName) {
		this.tagTypeName = tagTypeName;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}