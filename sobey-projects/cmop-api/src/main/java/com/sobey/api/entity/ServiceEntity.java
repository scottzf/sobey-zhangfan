package com.sobey.api.entity;

public class ServiceEntity {

	private String identifier;
	private String name;
	private String remark;

	public ServiceEntity(String identifier, String name, String remark) {
		super();
		this.identifier = identifier;
		this.name = name;
		this.remark = remark;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
