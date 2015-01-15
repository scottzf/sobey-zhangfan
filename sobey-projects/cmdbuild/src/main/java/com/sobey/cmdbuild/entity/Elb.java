package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.ServiceBasic;

/**
 * Elb generated by hbm2java
 */
@Entity
@Table(name = "elb", schema = "public")
public class Elb extends ServiceBasic {

	private Set<ElbHistory> elbHistories = new HashSet<ElbHistory>(0);
	private Integer ipaddress;
	private Integer subnet;

	public Elb() {
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "elb")
	public Set<ElbHistory> getElbHistories() {
		return elbHistories;
	}

	@Column(name = "ipaddress")
	public Integer getIpaddress() {
		return ipaddress;
	}

	@Column(name = "subnet")
	public Integer getSubnet() {
		return subnet;
	}

	public void setElbHistories(Set<ElbHistory> elbHistories) {
		this.elbHistories = elbHistories;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setSubnet(Integer subnet) {
		this.subnet = subnet;
	}

}
