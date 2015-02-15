package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.BasicDTO;

@XmlRootElement(name = "TagDTO")
@XmlType(name = "TagDTO", namespace = WsConstants.NS)
public class TagDTO extends BasicDTO {

	private Integer parentTag;
	private Integer tagType;
	private Integer tenants;

	public Integer getParentTag() {
		return parentTag;
	}

	public Integer getTagType() {
		return tagType;
	}

	public Integer getTenants() {
		return tenants;
	}

	public void setParentTag(Integer parentTag) {
		this.parentTag = parentTag;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}