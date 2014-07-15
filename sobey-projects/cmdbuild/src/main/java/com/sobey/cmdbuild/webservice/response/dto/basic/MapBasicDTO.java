package com.sobey.cmdbuild.webservice.response.dto.basic;

import java.util.Date;

/**
 * 
 * Map的基本类,每个MapDTO必须继承该抽象类
 * 
 * @author Administrator
 *
 */
public abstract class MapBasicDTO {

	protected Date beginDate;
	protected String code;
	protected String description;
	protected Date endDate;
	protected Integer id;
	protected String idObj1;
	protected String idObj2;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdObj1() {
		return idObj1;
	}

	public void setIdObj1(String idObj1) {
		this.idObj1 = idObj1;
	}

	public String getIdObj2() {
		return idObj2;
	}

	public void setIdObj2(String idObj2) {
		this.idObj2 = idObj2;
	}

}
