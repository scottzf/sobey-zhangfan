package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ServiceBasicDTO;

@XmlRootElement(name = "RouterDTO")
@XmlType(name = "RouterDTO", namespace = WsConstants.NS)
public class RouterDTO extends ServiceBasicDTO {

	private Integer ecs;
	private EcsDTO ecsDTO;

	public Integer getEcs() {
		return ecs;
	}

	public EcsDTO getEcsDTO() {
		return ecsDTO;
	}

	public void setEcs(Integer ecs) {
		this.ecs = ecs;
	}

	public void setEcsDTO(EcsDTO ecsDTO) {
		this.ecsDTO = ecsDTO;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}