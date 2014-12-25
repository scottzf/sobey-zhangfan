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
	private Integer storage;

	private String es3Status;
	private String thinProvisioned;
	private String totalSize;
	private String usedSize;
	private String usedSizePre;
	private String availableSize;
	private String snapshotSize;
	private String aggreName;
	private String maximumFiles;
	private String currentFiles;

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

	@Column(name = "storage")
	public Integer getStorage() {
		return storage;
	}

	public void setStorage(Integer storage) {
		this.storage = storage;
	}

	@Column(name = "es3_status", length = 100)
	public String getEs3Status() {
		return es3Status;
	}

	public void setEs3Status(String es3Status) {
		this.es3Status = es3Status;
	}

	@Column(name = "thin_provisioned", length = 100)
	public String getThinProvisioned() {
		return thinProvisioned;
	}

	public void setThinProvisioned(String thinProvisioned) {
		this.thinProvisioned = thinProvisioned;
	}

	@Column(name = "total_size", length = 200)
	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	@Column(name = "used_size", length = 100)
	public String getUsedSize() {
		return usedSize;
	}

	public void setUsedSize(String usedSize) {
		this.usedSize = usedSize;
	}

	@Column(name = "used_size_pre", length = 100)
	public String getUsedSizePre() {
		return usedSizePre;
	}

	public void setUsedSizePre(String usedSizePre) {
		this.usedSizePre = usedSizePre;
	}

	@Column(name = "available_size", length = 100)
	public String getAvailableSize() {
		return availableSize;
	}

	public void setAvailableSize(String availableSize) {
		this.availableSize = availableSize;
	}

	@Column(name = "snapshot_size", length = 100)
	public String getSnapshotSize() {
		return snapshotSize;
	}

	public void setSnapshotSize(String snapshotSize) {
		this.snapshotSize = snapshotSize;
	}

	@Column(name = "aggre_name", length = 100)
	public String getAggreName() {
		return aggreName;
	}

	public void setAggreName(String aggreName) {
		this.aggreName = aggreName;
	}

	@Column(name = "maximum_files", length = 100)
	public String getMaximumFiles() {
		return maximumFiles;
	}

	public void setMaximumFiles(String maximumFiles) {
		this.maximumFiles = maximumFiles;
	}

	@Column(name = "current_files", length = 100)
	public String getCurrentFiles() {
		return currentFiles;
	}

	public void setCurrentFiles(String currentFiles) {
		this.currentFiles = currentFiles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "es3")
	public Set<Es3History> getEs3Histories() {
		return es3Histories;
	}

	public void setEs3Histories(Set<Es3History> es3Histories) {
		this.es3Histories = es3Histories;
	}

}
