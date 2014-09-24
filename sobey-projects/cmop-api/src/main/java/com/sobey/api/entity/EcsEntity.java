package com.sobey.api.entity;

public class EcsEntity {

	private String identifier;
	private String ecsName;
	private String remark;
	private String idc;
	private String ipaddress;
	private String specification;

	public EcsEntity(String identifier, String ecsName, String remark, String idc, String ipaddress,
			String specification) {
		super();
		this.identifier = identifier;
		this.ecsName = ecsName;
		this.remark = remark;
		this.idc = idc;
		this.ipaddress = ipaddress;
		this.specification = specification;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getEcsName() {
		return ecsName;
	}

	public void setEcsName(String ecsName) {
		this.ecsName = ecsName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIdc() {
		return idc;
	}

	public void setIdc(String idc) {
		this.idc = idc;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

}
