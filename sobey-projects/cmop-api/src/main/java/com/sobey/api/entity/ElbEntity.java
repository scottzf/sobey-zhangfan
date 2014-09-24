package com.sobey.api.entity;

import java.util.List;

public class ElbEntity {

	private String identifier;
	private String elbName;
	private List<EcsEntity> ecsEntities;

	public ElbEntity(String identifier, String elbName, List<EcsEntity> ecsEntities) {
		super();
		this.identifier = identifier;
		this.elbName = elbName;
		this.ecsEntities = ecsEntities;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getElbName() {
		return elbName;
	}

	public void setElbName(String elbName) {
		this.elbName = elbName;
	}

	public List<EcsEntity> getEcsEntities() {
		return ecsEntities;
	}

	public void setEcsEntities(List<EcsEntity> ecsEntities) {
		this.ecsEntities = ecsEntities;
	}

}
