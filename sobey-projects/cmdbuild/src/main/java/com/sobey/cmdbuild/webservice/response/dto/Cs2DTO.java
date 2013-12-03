package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "Cs2DTO", namespace = WsConstants.NS)
public class Cs2DTO {

	private Date beginDate;
	private String code;
	private String description;
	private Double diskSize;
	private Integer fimas;
	private Integer id;
	private Integer ipaddress;
	private String remark;
	private Integer tag;
	private Integer tenants;

	private Integer es3Spec;
	private IpaddressDTO ipaddressDTO;
	private Es3SpecDTO es3SpecDTO;
	private TenantsDTO tenantsDTO;
	private FimasDTO fimasDTO;
	private TagDTO tagDTO;

	public Integer getEs3Spec() {
		return es3Spec;
	}

	public void setEs3Spec(Integer es3Spec) {
		this.es3Spec = es3Spec;
	}

	public IpaddressDTO getIpaddressDTO() {
		return ipaddressDTO;
	}

	public void setIpaddressDTO(IpaddressDTO ipaddressDTO) {
		this.ipaddressDTO = ipaddressDTO;
	}

	public Es3SpecDTO getEs3SpecDTO() {
		return es3SpecDTO;
	}

	public void setEs3SpecDTO(Es3SpecDTO es3SpecDTO) {
		this.es3SpecDTO = es3SpecDTO;
	}

	public TenantsDTO getTenantsDTO() {
		return tenantsDTO;
	}

	public void setTenantsDTO(TenantsDTO tenantsDTO) {
		this.tenantsDTO = tenantsDTO;
	}

	public FimasDTO getFimasDTO() {
		return fimasDTO;
	}

	public void setFimasDTO(FimasDTO fimasDTO) {
		this.fimasDTO = fimasDTO;
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

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Double getDiskSize() {
		return diskSize;
	}

	public Integer getFimas() {
		return fimas;
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

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDiskSize(Double diskSize) {
		this.diskSize = diskSize;
	}

	public void setFimas(Integer fimas) {
		this.fimas = fimas;
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