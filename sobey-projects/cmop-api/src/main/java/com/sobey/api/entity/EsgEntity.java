package com.sobey.api.entity;

import java.util.List;

public class EsgEntity {

	private String identifier;
	private String esgName;
	private Boolean isDefault;
	private List<EsgPolicyEntity> policyEntities;
	private List<EcsEntity> ecsEntities;

	public EsgEntity(String identifier, String esgName, Boolean isDefault, List<EsgPolicyEntity> policyEntities,
			List<EcsEntity> ecsEntities) {
		super();
		this.identifier = identifier;
		this.esgName = esgName;
		this.isDefault = isDefault;
		this.policyEntities = policyEntities;
		this.ecsEntities = ecsEntities;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getEsgName() {
		return esgName;
	}

	public void setEsgName(String esgName) {
		this.esgName = esgName;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public List<EsgPolicyEntity> getPolicyEntities() {
		return policyEntities;
	}

	public void setPolicyEntities(List<EsgPolicyEntity> policyEntities) {
		this.policyEntities = policyEntities;
	}

	public List<EcsEntity> getEcsEntities() {
		return ecsEntities;
	}

	public void setEcsEntities(List<EcsEntity> ecsEntities) {
		this.ecsEntities = ecsEntities;
	}

}
