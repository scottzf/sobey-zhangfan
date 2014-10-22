package com.sobey.api.entity;

public class EsgPolicyEntity {

	private String identifier;
	private String policyName;
	private String sourceIP;
	private String targetIP;
	private String policyType;

	public EsgPolicyEntity(String identifier, String policyName, String sourceIP, String targetIP, String policyType) {
		super();
		this.identifier = identifier;
		this.policyName = policyName;
		this.sourceIP = sourceIP;
		this.targetIP = targetIP;
		this.policyType = policyType;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getSourceIP() {
		return sourceIP;
	}

	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	public String getTargetIP() {
		return targetIP;
	}

	public void setTargetIP(String targetIP) {
		this.targetIP = targetIP;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

}
