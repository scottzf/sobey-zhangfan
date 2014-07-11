package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.PortBasicDTO;

@XmlRootElement(name = "LoadBalancerPortDTO")
@XmlType(name = "LoadBalancerPortDTO", namespace = WsConstants.NS)
public class LoadBalancerPortDTO extends PortBasicDTO {

	private Integer loadBalancer;
	private LoadBalancerDTO loadBalancerDTO;

	public Integer getLoadBalancer() {
		return loadBalancer;
	}

	public void setLoadBalancer(Integer loadBalancer) {
		this.loadBalancer = loadBalancer;
	}

	public LoadBalancerDTO getLoadBalancerDTO() {
		return loadBalancerDTO;
	}

	public void setLoadBalancerDTO(LoadBalancerDTO loadBalancerDTO) {
		this.loadBalancerDTO = loadBalancerDTO;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}