package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.ComponentBasic;

/**
 * StorageBox generated by hbm2java
 */
@Entity
@Table(name = "storage_box", schema = "public")
public class StorageBox extends ComponentBasic {

	private Integer diskNumber;
	private Integer diskType;
	private Set<StorageBoxHistory> storageBoxHistories = new HashSet<StorageBoxHistory>(0);

	public StorageBox() {
	}

	@Column(name = "disk_number")
	public Integer getDiskNumber() {
		return diskNumber;
	}

	@Column(name = "disk_type")
	public Integer getDiskType() {
		return diskType;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "storageBox")
	public Set<StorageBoxHistory> getStorageBoxHistories() {
		return storageBoxHistories;
	}

	public void setDiskNumber(Integer diskNumber) {
		this.diskNumber = diskNumber;
	}

	public void setDiskType(Integer diskType) {
		this.diskType = diskType;
	}

	public void setStorageBoxHistories(Set<StorageBoxHistory> storageBoxHistories) {
		this.storageBoxHistories = storageBoxHistories;
	}

}
