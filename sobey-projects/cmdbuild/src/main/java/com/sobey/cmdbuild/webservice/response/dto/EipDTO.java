package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement(name = "EipDTO")
@XmlType(name = "EipDTO", namespace = WsConstants.NS)
public class EipDTO {

	private Integer id;
	private String code;
	private String description;
	private Date beginDate;
	private String remark;
	private Integer tag;
	private TagDTO tagDTO;
	private Integer tenants;
	private TenantsDTO tenantsDTO;
	private Integer eipSpec;
	private EipSpecDTO eipSpecDTO;
	private Integer ipaddress;
	private IpaddressDTO ipaddressDTO;
	private Integer eipStatus;
	private String eipStatusText;
	private Integer bandwidth;

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

	public Integer getEipSpec() {
		return eipSpec;
	}

	public void setEipSpec(Integer eipSpec) {
		this.eipSpec = eipSpec;
	}

	public EipSpecDTO getEipSpecDTO() {
		return eipSpecDTO;
	}

	public void setEipSpecDTO(EipSpecDTO eipSpecDTO) {
		this.eipSpecDTO = eipSpecDTO;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public IpaddressDTO getIpaddressDTO() {
		return ipaddressDTO;
	}

	public void setIpaddressDTO(IpaddressDTO ipaddressDTO) {
		this.ipaddressDTO = ipaddressDTO;
	}

	public Integer getEipStatus() {
		return eipStatus;
	}

	public void setEipStatus(Integer eipStatus) {
		this.eipStatus = eipStatus;
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

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}