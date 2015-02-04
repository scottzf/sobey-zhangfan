package com.sobey.api.entity;

import java.util.List;

public class ElbEntity {

	private List<EcsEntity> ecsEntities;
	private String elbName;
	private String identifier;

	public ElbEntity(List<EcsEntity> ecsEntities, String elbName, String identifier) {
		super();
		this.ecsEntities = ecsEntities;
		this.elbName = elbName;
		this.identifier = identifier;
	}

	public List<EcsEntity> getEcsEntities() {
		return ecsEntities;
	}

	public String getElbName() {
		return elbName;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setEcsEntities(List<EcsEntity> ecsEntities) {
		this.ecsEntities = ecsEntities;
	}

	public void setElbName(String elbName) {
		this.elbName = elbName;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
