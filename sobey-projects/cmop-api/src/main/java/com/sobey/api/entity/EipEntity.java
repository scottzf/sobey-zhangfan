package com.sobey.api.entity;

public class EipEntity {

	private String identifier;
	private String eipName;
	private String remark;
	private String bandwidth;

	public EipEntity(String identifier, String eipName, String remark, String bandwidth) {
		super();
		this.identifier = identifier;
		this.eipName = eipName;
		this.remark = remark;
		this.bandwidth = bandwidth;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getEipName() {
		return eipName;
	}

	public void setEipName(String eipName) {
		this.eipName = eipName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}

}
