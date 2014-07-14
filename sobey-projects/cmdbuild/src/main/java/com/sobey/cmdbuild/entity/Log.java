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

	private Integer tenants;
	private Integer operateType;
	private Integer result;
	private Integer serviceType;

	private Set<LogHistory> logHistories = new HashSet<LogHistory>(0);

	public Log() {
	}

	@Column(name = "tenants")
	public Integer getTenants() {
		return tenants;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	@Column(name = "operate_type")
	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	@Column(name = "result")
	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	@Column(name = "service_type")
	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "log")
	public Set<LogHistory> getLogHistories() {
		return logHistories;
	}

	public void setLogHistories(Set<LogHistory> logHistories) {
		this.logHistories = logHistories;
	}

}
