package com.sobey.switches.webservice.response.dto;

import java.util.List;

import com.sobey.switches.webservice.pojo.SourceAndDest;

/**
 * 多租户SGP
 */
public class SgpDTO {

	/**
	 * 租户编号
	 */
	private Integer tenantsId;

	/**
	 * acl编号
	 */
	private Integer aclNumber;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 许可ip列表(必须有,数量不固定)
	 */
	private List<SourceAndDest> permit;

	/**
	 * 拒绝ip列表(必须有,数量不固定)
	 */
	private List<SourceAndDest> deny;

	public Integer getTenantsId() {
		return tenantsId;
	}

	public void setTenantsId(Integer tenantsId) {
		this.tenantsId = tenantsId;
	}

	public Integer getAclNumber() {
		return aclNumber;
	}

	public void setAclNumber(Integer aclNumber) {
		this.aclNumber = aclNumber;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<SourceAndDest> getPermit() {
		return permit;
	}

	public void setPermit(List<SourceAndDest> permit) {
		this.permit = permit;
	}

	public List<SourceAndDest> getDeny() {
		return deny;
	}

	public void setDeny(List<SourceAndDest> deny) {
		this.deny = deny;
	}

}
