package com.sobey.api.entity;

import java.util.List;

public class TagEntity {

	private String identifier;
	private String tagName;
	private List<ServiceEntity> serviceEntities;

	public TagEntity(String identifier, String tagName, List<ServiceEntity> serviceEntities) {
		super();
		this.identifier = identifier;
		this.tagName = tagName;
		this.serviceEntities = serviceEntities;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public List<ServiceEntity> getServiceEntities() {
		return serviceEntities;
	}

	public void setServiceEntities(List<ServiceEntity> serviceEntities) {
		this.serviceEntities = serviceEntities;
	}

}
