package com.sobey.api.entity;

import java.util.List;

public class DnsEntity {

	private String identifier;
	private String dnsName;
	private String remark;
	private List<EipEntity> eipEntities;

	public DnsEntity(String identifier, String dnsName, String remark, List<EipEntity> eipEntities) {
		super();
		this.identifier = identifier;
		this.dnsName = dnsName;
		this.remark = remark;
		this.eipEntities = eipEntities;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getDnsName() {
		return dnsName;
	}

	public void setDnsName(String dnsName) {
		this.dnsName = dnsName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<EipEntity> getEipEntities() {
		return eipEntities;
	}

	public void setEipEntities(List<EipEntity> eipEntities) {
		this.eipEntities = eipEntities;
	}

}
