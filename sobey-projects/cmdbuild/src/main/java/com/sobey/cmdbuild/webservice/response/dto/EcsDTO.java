package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ServiceBasicDTO;

@XmlRootElement(name = "EcsDTO")
@XmlType(name = "EcsDTO", namespace = WsConstants.NS)
public class EcsDTO extends ServiceBasicDTO {

	private Integer ecsSpec;
	private EcsSpecDTO ecsSpecDTO;
	private Integer ecsStatus;
	private String ecsStatusText;
	private Integer ipaddress;
	private IpaddressDTO ipaddressDTO;
	private Integer server;
	private ServerDTO ServerDTO;

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

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}