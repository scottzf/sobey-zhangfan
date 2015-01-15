package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.ServiceBasic;

@Entity
@Table(name = "dns", schema = "public")
public class Dns extends ServiceBasic {

	private String cnameDomain;
	private Set<DnsHistory> dnsHistories = new HashSet<DnsHistory>(0);
	private String domainName;
	private Integer domainType;

	public Dns() {
	}

	@Column(name = "\"cnameDomain\"", length = 100)
	public String getCnameDomain() {
		return cnameDomain;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dns")
	public Set<DnsHistory> getDnsHistories() {
		return dnsHistories;
	}

	@Column(name = "\"domainName\"", length = 100)
	public String getDomainName() {
		return domainName;
	}

	@Column(name = "domain_type")
	public Integer getDomainType() {
		return domainType;
	}

	public void setCnameDomain(String cnameDomain) {
		this.cnameDomain = cnameDomain;
	}

	public void setDnsHistories(Set<DnsHistory> dnsHistories) {
		this.dnsHistories = dnsHistories;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public void setDomainType(Integer domainType) {
		this.domainType = domainType;
	}

}
