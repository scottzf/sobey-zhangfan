package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement(name = "DnsDTO")
@XmlType(name = "DnsDTO", namespace = WsConstants.NS)
public class DnsDTO {

	private Integer id;
	private String code;
	private String description;
	private Date beginDate;
	private String remark;
	private Integer tag;
	private TagDTO tagDTO;
	private Integer tenants;
	private TenantsDTO tenantsDTO;
	private Integer domainType;
	private String domainTypeText;
	private String domainName;
	private String cnameDomain;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public TagDTO getTagDTO() {
		return tagDTO;
	}

	public void setTagDTO(TagDTO tagDTO) {
		this.tagDTO = tagDTO;
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

	public Integer getDomainType() {
		return domainType;
	}

	public void setDomainType(Integer domainType) {
		this.domainType = domainType;
	}

	public String getDomainTypeText() {
		return domainTypeText;
	}

	public void setDomainTypeText(String domainTypeText) {
		this.domainTypeText = domainTypeText;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getCnameDomain() {
		return cnameDomain;
	}

	public void setCnameDomain(String cnameDomain) {
		this.cnameDomain = cnameDomain;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}