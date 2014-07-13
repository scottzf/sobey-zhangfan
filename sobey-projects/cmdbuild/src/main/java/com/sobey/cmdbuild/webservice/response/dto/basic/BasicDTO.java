package com.sobey.cmdbuild.webservice.response.dto.basic;

import java.util.Date;

/**
 * 
 * DTO的基本类,每个DTO必须继承该抽象类
 * 
 * @author Administrator
 *
 */
public abstract class BasicDTO {

	protected Date beginDate;
	protected String code; // cmdbuild中唯一的值,对用户隐藏,由系统自动生成.
	protected String description; // 对用户显示的字段.
	protected Integer id;
	protected String remark;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
