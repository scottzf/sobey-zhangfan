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
	private TagDTO parentTagDTO;
	private Integer tagType;
	private String tagTypeText;
	private Integer tenants;
	private TenantsDTO tenantsDTO;

	public Integer getParentTag() {
		return parentTag;
	}

	public void setParentTag(Integer parentTag) {
		this.parentTag = parentTag;
	}

	public TagDTO getParentTagDTO() {
		return parentTagDTO;
	}

	public void setParentTagDTO(TagDTO parentTagDTO) {
		this.parentTagDTO = parentTagDTO;
	}

	public Integer getTagType() {
		return tagType;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}

	public String getTagTypeText() {
		return tagTypeText;
	}

	public void setTagTypeText(String tagTypeText) {
		this.tagTypeText = tagTypeText;
	}

	public Integer getTenants() {
		return tenants;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	public TenantsDTO getTenantsDTO() {
		return tenantsDTO;
	}

	public void setTenantsDTO(TenantsDTO tenantsDTO) {
		this.tenantsDTO = tenantsDTO;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}