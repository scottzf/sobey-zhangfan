package com.sobey.api.entity;

import java.util.List;

public class FirewallServiceEntity {

	private String identifier;
	private List<FirewallPolicyEntity> policyEntities;

	public FirewallServiceEntity(String identifier, List<FirewallPolicyEntity> policyEntities) {
		super();
		this.identifier = identifier;
		this.policyEntities = policyEntities;
	}

	public String getIdentifier() {
		return identifier;
	}

	public List<FirewallPolicyEntity> getPolicyEntities() {
		return policyEntities;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setPolicyEntities(List<FirewallPolicyEntity> policyEntities) {
		this.policyEntities = policyEntities;
	}

}
