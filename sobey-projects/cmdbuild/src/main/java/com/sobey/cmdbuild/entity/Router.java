package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.ServiceBasic;

@Entity
@Table(name = "router", schema = "public")
public class Router extends ServiceBasic {

	private Integer ecs;
	private Set<RouterHistory> routerHistories = new HashSet<RouterHistory>(0);

	public Router() {
	}

	@Column(name = "ecs")
	public Integer getEcs() {
		return ecs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "router")
	public Set<RouterHistory> getRouterHistories() {
		return routerHistories;
	}

	public void setEcs(Integer ecs) {
		this.ecs = ecs;
	}

	public void setRouterHistories(Set<RouterHistory> routerHistories) {
		this.routerHistories = routerHistories;
	}

}
