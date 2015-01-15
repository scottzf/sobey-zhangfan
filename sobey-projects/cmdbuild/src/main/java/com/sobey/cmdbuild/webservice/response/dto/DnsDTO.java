package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ServiceBasicDTO;

@XmlRootElement(name = "DnsDTO")
@XmlType(name = "DnsDTO", namespace = WsConstants.NS)
public class DnsDTO extends ServiceBasicDTO {

	private String cnameDomain;
	private String domainName;
	private Integer domainType;

	public String getCnameDomain() {
		return cnameDomain;
	}

	public String getDomainName() {
		return domainName;
	}

	public Integer getDomainType() {
		return domainType;
	}

	public void setCnameDomain(String cnameDomain) {
		this.cnameDomain = cnameDomain;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public void setDomainType(Integer domainType) {
		this.domainType = domainType;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}