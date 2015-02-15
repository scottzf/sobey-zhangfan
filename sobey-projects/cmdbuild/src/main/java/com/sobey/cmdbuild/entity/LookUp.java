package com.sobey.cmdbuild.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.BasicEntity;

@Entity
@Table(name = "\"LookUp\"", schema = "public")
public class LookUp extends BasicEntity {

	private Boolean isDefault;
	private Integer number;
	private Integer parentId;
	private String parentType;
	private String type;

	@Column(name = "\"IsDefault\"")
	public Boolean getIsDefault() {
		return isDefault;
	}

	@Column(name = "\"Number\"")
	public Integer getNumber() {
		return number;
	}

	@Column(name = "\"ParentId\"")
	public Integer getParentId() {
		return parentId;
	}

	@Column(name = "\"ParentType\"", length = 64)
	public String getParentType() {
		return parentType;
	}

	@Column(name = "\"Type\"", length = 64)
	public String getType() {
		return type;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public void setType(String type) {
		this.type = type;
	}

}
