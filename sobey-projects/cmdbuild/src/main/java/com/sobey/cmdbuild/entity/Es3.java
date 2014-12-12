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
@Table(name = "es3", schema = "public")
public class Es3 extends ServiceBasic {

	private String volumeName;
	private Integer es3Type;
	private Integer diskSize;
	private Integer storage;
	private Integer es3Status;
	private Set<Es3History> es3Histories = new HashSet<Es3History>(0);

	public Es3() {
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

	@Column(name = "es3_status")
	public Integer getEs3Status() {
		return es3Status;
	}

	public void setEs3Status(Integer es3Status) {
		this.es3Status = es3Status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "es3")
	public Set<Es3History> getEs3Histories() {
		return es3Histories;
	}

	public void setEs3Histories(Set<Es3History> es3Histories) {
		this.es3Histories = es3Histories;
	}

}
