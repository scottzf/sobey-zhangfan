package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "EcsDTO", namespace = WsConstants.NS)
public class EcsDTO {

	private Date beginDate;
	private String code;
	private String description;
	private Integer ecsAgent;
	private Integer ecsStatus;

	private Integer id;
	private Integer image;
	private Integer ipaddress;
	private String remark;
	private Integer server;
	private Integer serviceSpec;
	private Integer tag;
	private Integer tenants;

	private Integer ecsSpec;
	private String imageText;
	private String ecsAgentText;
	private String ecsStatusText;

	private IpaddressDTO ipaddressDTO;
	private EcsSpecDTO ecsSpecDTO;
	private ServerDTO ServerDTO;
	private TenantsDTO tenantsDTO;
	private TagDTO tagDTO;

	public Integer getEcsSpec() {
		return ecsSpec;
	}

	public void setEcsSpec(Integer ecsSpec) {
		this.ecsSpec = ecsSpec;
	}

	public String getImageText() {
		return imageText;
	}

	public void setImageText(String imageText) {
		this.imageText = imageText;
	}

	public String getEcsAgentText() {
		return ecsAgentText;
	}

	public void setEcsAgentText(String ecsAgentText) {
		this.ecsAgentText = ecsAgentText;
	}

	public String getEcsStatusText() {
		return ecsStatusText;
	}

	public void setEcsStatusText(String ecsStatusText) {
		this.ecsStatusText = ecsStatusText;
	}

	public IpaddressDTO getIpaddressDTO() {
		return ipaddressDTO;
	}

	public void setIpaddressDTO(IpaddressDTO ipaddressDTO) {
		this.ipaddressDTO = ipaddressDTO;
	}

	public EcsSpecDTO getEcsSpecDTO() {
		return ecsSpecDTO;
	}

	public void setEcsSpecDTO(EcsSpecDTO ecsSpecDTO) {
		this.ecsSpecDTO = ecsSpecDTO;
	}

	public ServerDTO getServerDTO() {
		return ServerDTO;
	}

	public void setServerDTO(ServerDTO serverDTO) {
		ServerDTO = serverDTO;
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

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Integer getEcsAgent() {
		return ecsAgent;
	}

	public Integer getEcsStatus() {
		return ecsStatus;
	}

	public Integer getId() {
		return id;
	}

	public Integer getImage() {
		return image;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getServer() {
		return server;
	}

	public Integer getServiceSpec() {
		return serviceSpec;
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

	public void setEcsAgent(Integer ecsAgent) {
		this.ecsAgent = ecsAgent;
	}

	public void setEcsStatus(Integer ecsStatus) {
		this.ecsStatus = ecsStatus;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setImage(Integer image) {
		this.image = image;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setServer(Integer server) {
		this.server = server;
	}

	public void setServiceSpec(Integer serviceSpec) {
		this.serviceSpec = serviceSpec;
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