package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.ServiceBasic;

@Entity
@Table(name = "router", schema = "public")
public class Router extends ServiceBasic {

	private Set<RouterHistory> routerHistories = new HashSet<RouterHistory>(0);

	public Router() {
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "router")
	public Set<RouterHistory> getRouterHistories() {
		return routerHistories;
	}

	public void setRouterHistories(Set<RouterHistory> routerHistories) {
		this.routerHistories = routerHistories;
	}

}
