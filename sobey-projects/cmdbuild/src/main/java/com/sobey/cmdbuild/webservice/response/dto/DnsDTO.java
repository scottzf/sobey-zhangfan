package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "DnsDTO", namespace = WsConstants.NS)
public class DnsDTO {

	private Date beginDate;
	private String cnameDomain;
	private String code;
	private String description;
	private String domainName;

	private Integer domainType;
	private Integer id;
	private String remark;
	private Integer tag;
	private Integer tenants;

	private String domainTypeText;
	private TenantsDTO tenantsDTO;
	private TagDTO tagDTO;

	public String getDomainTypeText() {
		return domainTypeText;
	}

	public void setDomainTypeText(String domainTypeText) {
		this.domainTypeText = domainTypeText;
	}

	public TenantsDTO getTenantsDTO() {
		return tenantsDTO;
	}

	public void setTenantsDTO(TenantsDTO tenantsDTO) {
		this.tenantsDTO = tenantsDTO;
	}

	public TagDTO getTagDTO() {
		return tagDTO;
	}

	public void setTagDTO(TagDTO tagDTO) {
		this.tagDTO = tagDTO;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public String getCnameDomain() {
		return cnameDomain;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getDomainName() {
		return domainName;
	}

	public Integer getDomainType() {
		return domainType;
	}

	public Integer getId() {
		return id;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getTag() {
		return tag;
	}

	public Integer getTenants() {
		return tenants;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setCnameDomain(String cnameDomain) {
		this.cnameDomain = cnameDomain;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public void setDomainType(Integer domainType) {
		this.domainType = domainType;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
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