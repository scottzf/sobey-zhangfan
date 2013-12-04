package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "EipDTO", namespace = WsConstants.NS)
public class EipDTO {

	private Integer bandwidth;
	private Date beginDate;
	private String code;
	private String description;
	private Integer eipSpec;

	private Integer eipStatus;
	private Integer id;
	private Integer ipaddress;
	private String remark;
	private Integer tag;
	private Integer tenants;

	private TenantsDTO tenantsDTO;
	private EipSpecDTO eipSpecDTO;
	private TagDTO tagDTO;
	private IpaddressDTO ipaddressDTO;
	private String eipStatusText;

	public TenantsDTO getTenantsDTO() {
		return tenantsDTO;
	}

	public void setTenantsDTO(TenantsDTO tenantsDTO) {
		this.tenantsDTO = tenantsDTO;
	}

	public EipSpecDTO getEipSpecDTO() {
		return eipSpecDTO;
	}

	public void setEipSpecDTO(EipSpecDTO eipSpecDTO) {
		this.eipSpecDTO = eipSpecDTO;
	}

	public TagDTO getTagDTO() {
		return tagDTO;
	}

	public void setTagDTO(TagDTO tagDTO) {
		this.tagDTO = tagDTO;
	}

	public IpaddressDTO getIpaddressDTO() {
		return ipaddressDTO;
	}

	public void setIpaddressDTO(IpaddressDTO ipaddressDTO) {
		this.ipaddressDTO = ipaddressDTO;
	}

	public String getEipStatusText() {
		return eipStatusText;
	}

	public void setEipStatusText(String eipStatusText) {
		this.eipStatusText = eipStatusText;
	}

	public Integer getBandwidth() {
		return bandwidth;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Integer getEipSpec() {
		return eipSpec;
	}

	public Integer getEipStatus() {
		return eipStatus;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIpaddress() {
		return ipaddress;
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

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEipSpec(Integer eipSpec) {
		this.eipSpec = eipSpec;
	}

	public void setEipStatus(Integer eipStatus) {
		this.eipStatus = eipStatus;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
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