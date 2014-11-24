package com.sobey.dns.webservice.response.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.dns.constans.WsConstants;

@XmlRootElement(name = "DnsSync")
@XmlType(name = "DnsSync", namespace = WsConstants.NS)
public class DnsSync {

	private String cnameDomain;
	private String domainName;
	private String domainType;
	private List<DnsPolicySync> policySyncs;

	public String getCnameDomain() {
		return cnameDomain;
	}

	public void setCnameDomain(String cnameDomain) {
		this.cnameDomain = cnameDomain;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getDomainType() {
		return domainType;
	}

	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}

	public List<DnsPolicySync> getPolicySyncs() {
		return policySyncs;
	}

	public void setPolicySyncs(List<DnsPolicySync> policySyncs) {
		this.policySyncs = policySyncs;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}