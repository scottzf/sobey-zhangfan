package com.sobey.api.entity;

import java.util.List;

public class RouterEntity {

	private String identifier;
	private String routerName;
	private List<SubnetEntity> subnetEntities;

	public RouterEntity(String identifier, String routerName, List<SubnetEntity> subnetEntities) {
		super();
		this.identifier = identifier;
		this.routerName = routerName;
		this.subnetEntities = subnetEntities;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getRouterName() {
		return routerName;
	}

	public List<SubnetEntity> getSubnetEntities() {
		return subnetEntities;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

	public void setSubnetEntities(List<SubnetEntity> subnetEntities) {
		this.subnetEntities = subnetEntities;
	}

}
