package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement(name = "LookUpDTO")
@XmlType(name = "LookUpDTO", namespace = WsConstants.NS)
public class LookUpDTO {

	private Date beginDate;
	private String code;
	private String description;
	private Integer id;
	private Boolean isDefault;
	private String notes;
	private Integer number;
	private Integer parentId;
	private String parentType;
	private String type;

	public Date getBeginDate() {
		return beginDate;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Integer getId() {
		return id;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public String getNotes() {
		return notes;
	}

	public Integer getNumber() {
		return number;
	}

	public Integer getParentId() {
		return parentId;
	}

	public String getParentType() {
		return parentType;
	}

	public String getType() {
		return type;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}