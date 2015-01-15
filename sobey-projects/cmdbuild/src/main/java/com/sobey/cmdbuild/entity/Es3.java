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

	private String aggreName;
	private String availableSize;
	private String currentFiles;
	private String diskFilePath;
	private Set<Es3History> es3Histories = new HashSet<Es3History>(0);
	private String es3Status;
	private Integer es3Type;
	private String maximumFiles;
	private String snapshotSize;
	private Integer storage;
	private Integer subnet;
	private String thinProvisioned;
	private String totalSize;
	private String usedSize;
	private String usedSizePre;
	private String volumeName;

	public Es3() {
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

	@Column(name = "disk_file_path", length = 200)
	public String getDiskFilePath() {
		return diskFilePath;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "es3")
	public Set<Es3History> getEs3Histories() {
		return es3Histories;
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

	@Column(name = "subnet")
	public Integer getSubnet() {
		return subnet;
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

	public void setDiskFilePath(String diskFilePath) {
		this.diskFilePath = diskFilePath;
	}

	public void setEs3Histories(Set<Es3History> es3Histories) {
		this.es3Histories = es3Histories;
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

	public void setSubnet(Integer subnet) {
		this.subnet = subnet;
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
