package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ServiceBasicDTO;

@XmlRootElement(name = "EsgDTO")
@XmlType(name = "EsgDTO", namespace = WsConstants.NS)
public class EsgDTO extends ServiceBasicDTO {

	private Integer aclNumber;
	private Boolean isDefault;

	public Integer getAclNumber() {
		return aclNumber;
	}

	public void setAclNumber(Integer aclNumber) {
		this.aclNumber = aclNumber;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}