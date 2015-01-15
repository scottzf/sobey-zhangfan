package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ServiceBasicDTO;

@XmlRootElement(name = "Es3DTO")
@XmlType(name = "Es3DTO", namespace = WsConstants.NS)
public class Es3DTO extends ServiceBasicDTO {

	private String aggreName;
	private String availableSize;
	private String currentFiles;
	private String diskFilePath;
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

	public String getAggreName() {
		return aggreName;
	}

	public String getAvailableSize() {
		return availableSize;
	}

	public String getCurrentFiles() {
		return currentFiles;
	}

	public String getDiskFilePath() {
		return diskFilePath;
	}

	public String getEs3Status() {
		return es3Status;
	}

	public Integer getEs3Type() {
		return es3Type;
	}

	public String getMaximumFiles() {
		return maximumFiles;
	}

	public String getSnapshotSize() {
		return snapshotSize;
	}

	public Integer getStorage() {
		return storage;
	}

	public Integer getSubnet() {
		return subnet;
	}

	public String getThinProvisioned() {
		return thinProvisioned;
	}

	public String getTotalSize() {
		return totalSize;
	}

	public String getUsedSize() {
		return usedSize;
	}

	public String getUsedSizePre() {
		return usedSizePre;
	}

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

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}