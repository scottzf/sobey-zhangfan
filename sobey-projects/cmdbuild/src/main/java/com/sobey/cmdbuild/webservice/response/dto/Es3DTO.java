package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ServiceBasicDTO;

@XmlRootElement(name = "Es3DTO")
@XmlType(name = "Es3DTO", namespace = WsConstants.NS)
public class Es3DTO extends ServiceBasicDTO {

	private Double diskSize;
	private Integer es3Type;
	private String es3TypeText;
	private Integer storage;
	private StorageDTO storageDTO;
	private String volumeName;

	public Double getDiskSize() {
		return diskSize;
	}

	public void setDiskSize(Double diskSize) {
		this.diskSize = diskSize;
	}

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

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}