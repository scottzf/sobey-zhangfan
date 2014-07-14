package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.DeviceBasic;

/**
 * Storage generated by hbm2java
 */
@Entity
@Table(name = "storage", schema = "public")
public class Storage extends DeviceBasic {

	private String configText;
	private String password;
	private Set<StorageHistory> storageHistories = new HashSet<StorageHistory>(0);

	public Storage() {
	}

	@Column(name = "config_text", length = 100)
	public String getConfigText() {
		return configText;
	}

	public void setConfigText(String configText) {
		this.configText = configText;
	}

	@Column(name = "password", length = 100)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "storage")
	public Set<StorageHistory> getStorageHistories() {
		return storageHistories;
	}

	public void setStorageHistories(Set<StorageHistory> storageHistories) {
		this.storageHistories = storageHistories;
	}

}
