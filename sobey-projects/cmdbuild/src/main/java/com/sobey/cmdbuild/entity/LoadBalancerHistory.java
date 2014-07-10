package com.sobey.cmdbuild.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.DeviceHistoryBasic;

/**
 * LoadBalancerHistory generated by hbm2java
 */
@Entity
@Table(name = "load_balancer_history", schema = "public")
public class LoadBalancerHistory extends DeviceHistoryBasic {

	private LoadBalancer loadBalancer;

	public LoadBalancerHistory() {
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public LoadBalancer getLoadBalancer() {
		return this.loadBalancer;
	}

	public void setLoadBalancer(LoadBalancer loadBalancer) {
		this.loadBalancer = loadBalancer;
	}

}
