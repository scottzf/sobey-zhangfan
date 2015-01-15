package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.BasicEntity;

@Entity
@Table(name = "log", schema = "public")
public class Log extends BasicEntity {

	private Set<LogHistory> logHistories = new HashSet<LogHistory>(0);
	private Integer operateType;
	private Integer result;
	private Integer serviceType;

	private Integer tenants;

	public Log() {
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "log")
	public Set<LogHistory> getLogHistories() {
		return logHistories;
	}

	@Column(name = "operate_type")
	public Integer getOperateType() {
		return operateType;
	}

	@Column(name = "result")
	public Integer getResult() {
		return result;
	}

	@Column(name = "service_type")
	public Integer getServiceType() {
		return serviceType;
	}

	@Column(name = "tenants")
	public Integer getTenants() {
		return tenants;
	}

	public void setLogHistories(Set<LogHistory> logHistories) {
		this.logHistories = logHistories;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

}
