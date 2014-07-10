package com.sobey.cmdbuild.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.DeviceHistoryBasic;

/**
 * ServerHistory generated by hbm2java
 */
@Entity
@Table(name = "server_history", schema = "public")
public class ServerHistory extends DeviceHistoryBasic {

	private Server server;
	private String resgroup;

	public ServerHistory() {
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	@Column(name = "resgroup", length = 100)
	public String getResgroup() {
		return resgroup;
	}

	public void setResgroup(String resgroup) {
		this.resgroup = resgroup;
	}

}
