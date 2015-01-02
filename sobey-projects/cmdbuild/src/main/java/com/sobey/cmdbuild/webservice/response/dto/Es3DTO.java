package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ServiceBasicDTO;

@XmlRootElement(name = "Es3DTO")
@XmlType(name = "Es3DTO", namespace = WsConstants.NS)
public class Es3DTO extends ServiceBasicDTO {

	private Integer es3Type;
	private String es3TypeText;
	private Integer storage;
	private StorageDTO storageDTO;
	private String volumeName;
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

	public Integer getEs3Type() {
		return es3Type;
	}

	public void setEs3Type(Integer es3Type) {
		this.es3Type = es3Type;
	}

	public String getEs3TypeText() {
		return es3TypeText;
	}

	public void setEs3TypeText(String es3TypeText) {
		this.es3TypeText = es3TypeText;
	}

	public Integer getStorage() {
		return storage;
	}

	public void setStorage(Integer storage) {
		this.storage = storage;
	}

	public StorageDTO getStorageDTO() {
		return storageDTO;
	}

	public void setStorageDTO(StorageDTO storageDTO) {
		this.storageDTO = storageDTO;
	}

	public String getVolumeName() {
		return volumeName;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	public String getEs3Status() {
		return es3Status;
	}

	public void setEs3Status(String es3Status) {
		this.es3Status = es3Status;
	}

	public String getThinProvisioned() {
		return thinProvisioned;
	}

	public void setThinProvisioned(String thinProvisioned) {
		this.thinProvisioned = thinProvisioned;
	}

	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	public String getUsedSize() {
		return usedSize;
	}

	public void setUsedSize(String usedSize) {
		this.usedSize = usedSize;
	}

	public String getUsedSizePre() {
		return usedSizePre;
	}

	public void setUsedSizePre(String usedSizePre) {
		this.usedSizePre = usedSizePre;
	}

	public String getAvailableSize() {
		return availableSize;
	}

	public void setAvailableSize(String availableSize) {
		this.availableSize = availableSize;
	}

	public String getSnapshotSize() {
		return snapshotSize;
	}

	public void setSnapshotSize(String snapshotSize) {
		this.snapshotSize = snapshotSize;
	}

	public String getAggreName() {
		return aggreName;
	}

	public void setAggreName(String aggreName) {
		this.aggreName = aggreName;
	}

	public String getMaximumFiles() {
		return maximumFiles;
	}

	public void setMaximumFiles(String maximumFiles) {
		this.maximumFiles = maximumFiles;
	}

	public String getCurrentFiles() {
		return currentFiles;
	}

	public void setCurrentFiles(String currentFiles) {
		this.currentFiles = currentFiles;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}