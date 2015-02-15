package com.sobey.api.entity;

public class Es3Entity {

	private String diskSize;
	private String es3Name;
	private String es3Type;
	private String identifier;
	private String remark;

	public Es3Entity(String diskSize, String es3Name, String es3Type, String identifier, String remark) {
		super();
		this.diskSize = diskSize;
		this.es3Name = es3Name;
		this.es3Type = es3Type;
		this.identifier = identifier;
		this.remark = remark;
	}

	public String getDiskSize() {
		return diskSize;
	}

	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}

	public String getEs3Name() {
		return es3Name;
	}

	public void setEs3Name(String es3Name) {
		this.es3Name = es3Name;
	}

	public String getEs3Type() {
		return es3Type;
	}

	public void setEs3Type(String es3Type) {
		this.es3Type = es3Type;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
