package com.sobey.storage.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.storage.constans.WsConstants;

/**
 * netapp上,执行脚本所需参数的对象:
 * 
 * <pre>
 * volumeName		卷名
 * volumeSize		卷大小(MB)
 * clientIPaddress		客户端IP
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "CreateEs3Parameter")
@XmlType(name = "CreateEs3Parameter", namespace = WsConstants.NS)
public class CreateEs3Parameter {

	/**
	 * 卷名
	 */
	private String volumeName;

	/**
	 * 卷大小(MB)
	 */
	private String volumeSize;

	/**
	 * 客户端IP
	 */
	private String clientIPaddress;

	public String getVolumeName() {
		return volumeName;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	public String getVolumeSize() {
		return volumeSize;
	}

	public void setVolumeSize(String volumeSize) {
		this.volumeSize = volumeSize;
	}

	public String getClientIPaddress() {
		return clientIPaddress;
	}

	public void setClientIPaddress(String clientIPaddress) {
		this.clientIPaddress = clientIPaddress;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
