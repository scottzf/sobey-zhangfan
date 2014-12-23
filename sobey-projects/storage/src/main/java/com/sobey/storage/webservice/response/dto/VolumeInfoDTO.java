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
	 * 空间总大小(GB)
	 */
	private String totalSize;

	/**
	 * 已用空间大小(GB)
	 */
	private String usedSize;

	/**
	 * 可用空间大小(GB)
	 */
	private String availableSize;

	/**
	 * snapshot保留块大小(GB)
	 */
	private String snapshotBlocksReservedSize;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAvailableSize() {
		return availableSize;
	}

	public void setAvailableSize(String availableSize) {
		this.availableSize = availableSize;
	}

	public String getSnapshotBlocksReservedSize() {
		return snapshotBlocksReservedSize;
	}

	public void setSnapshotBlocksReservedSize(String snapshotBlocksReservedSize) {
		this.snapshotBlocksReservedSize = snapshotBlocksReservedSize;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}