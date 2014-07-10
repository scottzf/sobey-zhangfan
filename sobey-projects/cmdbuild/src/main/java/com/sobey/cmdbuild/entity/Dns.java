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

	private Integer domainType;
	private String domainName;
	private String cnameDomain;
	private Set<DnsHistory> dnsHistories = new HashSet<DnsHistory>(0);

	public Dns() {
	}

	@Column(name = "domain_type")
	public Integer getDomainType() {
		return domainType;
	}

	public void setDomainType(Integer domainType) {
		this.domainType = domainType;
	}

	@Column(name = "\"domainName\"", length = 100)
	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	@Column(name = "\"cnameDomain\"", length = 100)
	public String getCnameDomain() {
		return cnameDomain;
	}

	public void setCnameDomain(String cnameDomain) {
		this.cnameDomain = cnameDomain;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dns")
	public Set<DnsHistory> getDnsHistories() {
		return dnsHistories;
	}

	public void setDnsHistories(Set<DnsHistory> dnsHistories) {
		this.dnsHistories = dnsHistories;
	}

}
