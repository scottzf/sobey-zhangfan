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

	private String aggreName;
	private String availableSize;
	private String currentFiles;
	private Es3 es3;
	private String es3Status;
	private Integer es3Type;
	private String maximumFiles;
	private String snapshotSize;
	private Integer storage;
	private String thinProvisioned;
	private String totalSize;
	private String usedSize;
	private String usedSizePre;
	private String volumeName;

	private Es3History() {
	}

	@Column(name = "aggre_name", length = 100)
	public String getAggreName() {
		return aggreName;
	}

	@Column(name = "available_size", length = 100)
	public String getAvailableSize() {
		return availableSize;
	}

	@Column(name = "current_files", length = 100)
	public String getCurrentFiles() {
		return currentFiles;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public Es3 getEs3() {
		return es3;
	}

	@Column(name = "es3_status", length = 100)
	public String getEs3Status() {
		return es3Status;
	}

	@Column(name = "es3_type")
	public Integer getEs3Type() {
		return es3Type;
	}

	@Column(name = "maximum_files", length = 100)
	public String getMaximumFiles() {
		return maximumFiles;
	}

	@Column(name = "snapshot_size", length = 100)
	public String getSnapshotSize() {
		return snapshotSize;
	}

	@Column(name = "storage")
	public Integer getStorage() {
		return storage;
	}

	@Column(name = "thin_provisioned", length = 100)
	public String getThinProvisioned() {
		return thinProvisioned;
	}

	@Column(name = "total_size", length = 200)
	public String getTotalSize() {
		return totalSize;
	}

	@Column(name = "used_size", length = 100)
	public String getUsedSize() {
		return usedSize;
	}

	@Column(name = "used_size_pre", length = 100)
	public String getUsedSizePre() {
		return usedSizePre;
	}

	@Column(name = "volume_name", length = 100)
	public String getVolumeName() {
		return volumeName;
	}

	public void setAggreName(String aggreName) {
		this.aggreName = aggreName;
	}

	public void setAvailableSize(String availableSize) {
		this.availableSize = availableSize;
	}

	public void setCurrentFiles(String currentFiles) {
		this.currentFiles = currentFiles;
	}

	public void setEs3(Es3 es3) {
		this.es3 = es3;
	}

	public void setEs3Status(String es3Status) {
		this.es3Status = es3Status;
	}

	public void setEs3Type(Integer es3Type) {
		this.es3Type = es3Type;
	}

	public void setMaximumFiles(String maximumFiles) {
		this.maximumFiles = maximumFiles;
	}

	public void setSnapshotSize(String snapshotSize) {
		this.snapshotSize = snapshotSize;
	}

	public void setStorage(Integer storage) {
		this.storage = storage;
	}

	public void setThinProvisioned(String thinProvisioned) {
		this.thinProvisioned = thinProvisioned;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	public void setUsedSize(String usedSize) {
		this.usedSize = usedSize;
	}

	public void setUsedSizePre(String usedSizePre) {
		this.usedSizePre = usedSizePre;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

}
