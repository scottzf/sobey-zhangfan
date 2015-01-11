package com.sobey.api.entity;

import java.util.List;

public class RouterEntity {

	private String routerName;
	private List<SubnetEntity> subnetEntities;

	public RouterEntity(String routerName, List<SubnetEntity> subnetEntities) {
		super();
		this.routerName = routerName;
		this.subnetEntities = subnetEntities;
	}

	public String getRouterName() {
		return routerName;
	}

	public List<SubnetEntity> getSubnetEntities() {
		return subnetEntities;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

	public void setSubnetEntities(List<SubnetEntity> subnetEntities) {
		this.subnetEntities = subnetEntities;
	}

}
