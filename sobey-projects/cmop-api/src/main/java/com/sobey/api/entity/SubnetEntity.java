package com.sobey.api.entity;

public class SubnetEntity {

	private String datacenter;
	private String gateway;
	private String identifier;
	private String netMask;
	private String remark;
	private String segment;
	private String subnetName;

	public SubnetEntity(String datacenter, String gateway, String identifier, String netMask, String remark,
			String segment, String subnetName) {
		super();
		this.datacenter = datacenter;
		this.gateway = gateway;
		this.identifier = identifier;
		this.netMask = netMask;
		this.remark = remark;
		this.segment = segment;
		this.subnetName = subnetName;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public String getGateway() {
		return gateway;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getNetMask() {
		return netMask;
	}

	public String getRemark() {
		return remark;
	}

	public String getSegment() {
		return segment;
	}

	public String getSubnetName() {
		return subnetName;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public void setSubnetName(String subnetName) {
		this.subnetName = subnetName;
	}

}
