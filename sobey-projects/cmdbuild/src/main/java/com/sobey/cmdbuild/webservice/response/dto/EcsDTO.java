package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement(name = "EcsDTO")
@XmlType(name = "EcsDTO", namespace = WsConstants.NS)
public class EcsDTO {

	private Integer id;
	private String code;
	private String description;
	private Date beginDate;
	private String remark;
	private Integer tag;
	private TagDTO tagDTO;
	private Integer ipaddress;
	private IpaddressDTO ipaddressDTO;
	private Integer ecsSpec;
	private EcsSpecDTO ecsSpecDTO;
	private Integer tenants;
	private TenantsDTO tenantsDTO;
	private Integer server;
	private ServerDTO ServerDTO;
	private Integer image;
	private String imageText;
	private Integer ecsAgent;
	private String ecsAgentText;
	private Integer ecsStatus;
	private String ecsStatusText;

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

	public Integer getEcsSpec() {
		return ecsSpec;
	}

	public void setEcsSpec(Integer ecsSpec) {
		this.ecsSpec = ecsSpec;
	}

	public EcsSpecDTO getEcsSpecDTO() {
		return ecsSpecDTO;
	}

	public void setEcsSpecDTO(EcsSpecDTO ecsSpecDTO) {
		this.ecsSpecDTO = ecsSpecDTO;
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

	public Integer getServer() {
		return server;
	}

	public void setServer(Integer server) {
		this.server = server;
	}

	public ServerDTO getServerDTO() {
		return ServerDTO;
	}

	public void setServerDTO(ServerDTO serverDTO) {
		ServerDTO = serverDTO;
	}

	public Integer getImage() {
		return image;
	}

	public void setImage(Integer image) {
		this.image = image;
	}

	public String getImageText() {
		return imageText;
	}

	public void setImageText(String imageText) {
		this.imageText = imageText;
	}

	public Integer getEcsAgent() {
		return ecsAgent;
	}

	public void setEcsAgent(Integer ecsAgent) {
		this.ecsAgent = ecsAgent;
	}

	public String getEcsAgentText() {
		return ecsAgentText;
	}

	public void setEcsAgentText(String ecsAgentText) {
		this.ecsAgentText = ecsAgentText;
	}

	public Integer getEcsStatus() {
		return ecsStatus;
	}

	public void setEcsStatus(Integer ecsStatus) {
		this.ecsStatus = ecsStatus;
	}

	public String getEcsStatusText() {
		return ecsStatusText;
	}

	public void setEcsStatusText(String ecsStatusText) {
		this.ecsStatusText = ecsStatusText;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}