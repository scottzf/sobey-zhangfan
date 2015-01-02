package com.sobey.storage.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.storage.constans.WsConstants;

@XmlRootElement(name = "VolumeInfoDTO")
@XmlType(name = "VolumeInfoDTO", namespace = WsConstants.NS)
public class VolumeInfoDTO {

	/**
	 * 卷名
	 */
	private String name;

	/**
	 * 卷状态
	 */
	private String status;

	/**
	 * 最大文件数
	 */
	private String maximumFiles;

	/**
	 * 当前文件数
	 */
	private String currentFiles;

	/**
	 * 聚合名
	 */
	private String aggregateName;

	/**
	 * 卷类型
	 */
	private String type;

	/**
	 * 是否是精简模式
	 */
	private String isThinProvisioned;

	/**
	 * 空间总大小(GB),不包含snapshot大小.
	 */
	private String totalSize;

	/**
	 * 已用空间大小(GB)
	 */
	private String usedSize;

	/**
	 * 已用空间百分比(%)
	 */
	private String usedSizePre;

	/**
	 * 可用空间大小(GB)
	 */
	private String availableSize;

	/**
	 * snapshot保留块大小(GB)
	 */
	private String snapshotSize;

	public VolumeInfoDTO() {
	}

	public VolumeInfoDTO(String name, String status, String maximumFiles, String currentFiles, String aggregateName,
			String type, String isThinProvisioned, String totalSize, String usedSize, String usedSizePre,
			String availableSize, String snapshotSize) {
		super();
		this.name = name;
		this.status = status;
		this.maximumFiles = maximumFiles;
		this.currentFiles = currentFiles;
		this.aggregateName = aggregateName;
		this.type = type;
		this.isThinProvisioned = isThinProvisioned;
		this.totalSize = totalSize;
		this.usedSize = usedSize;
		this.usedSizePre = usedSizePre;
		this.availableSize = availableSize;
		this.snapshotSize = snapshotSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getAggregateName() {
		return aggregateName;
	}

	public void setAggregateName(String aggregateName) {
		this.aggregateName = aggregateName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsThinProvisioned() {
		return isThinProvisioned;
	}

	public void setIsThinProvisioned(String isThinProvisioned) {
		this.isThinProvisioned = isThinProvisioned;
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

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}