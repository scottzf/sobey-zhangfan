package com.sobey.cmdbuild.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.ServiceHistoryBasic;

@Entity
@Table(name = "es3_history", schema = "public")
public class Es3History extends ServiceHistoryBasic {

	private Es3 es3;
	private String volumeName;
	private Integer es3Type;
	private Integer diskSize;
	private Integer storage;

	private Es3History() {
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public Es3 getEs3() {
		return es3;
	}

	public void setEs3(Es3 es3) {
		this.es3 = es3;
	}

	@Column(name = "volume_name", length = 100)
	public String getVolumeName() {
		return volumeName;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	@Column(name = "es3_type")
	public Integer getEs3Type() {
		return es3Type;
	}

	public void setEs3Type(Integer es3Type) {
		this.es3Type = es3Type;
	}

	@Column(name = "disk_size")
	public Integer getDiskSize() {
		return diskSize;
	}

	public void setDiskSize(Integer diskSize) {
		this.diskSize = diskSize;
	}

	@Column(name = "storage")
	public Integer getStorage() {
		return storage;
	}

	public void setStorage(Integer storage) {
		this.storage = storage;
	}

}
