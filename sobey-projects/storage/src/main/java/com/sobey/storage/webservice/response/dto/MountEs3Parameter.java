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
 * clientIPaddress		客户端IP
 * netAppIPaddress		netApp Ip
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "MountEs3Parameter")
@XmlType(name = "MountEs3Parameter", namespace = WsConstants.NS)
public class MountEs3Parameter {

	/**
	 * 卷名
	 */
	private String volumeName;

	/**
	 * 客户端IP
	 */
	private String clientIPaddress;

	/**
	 * netapp IP
	 */
	private String netAppIPaddress;

	public String getVolumeName() {
		return volumeName;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	public String getClientIPaddress() {
		return clientIPaddress;
	}

	public void setClientIPaddress(String clientIPaddress) {
		this.clientIPaddress = clientIPaddress;
	}

	public String getNetAppIPaddress() {
		return netAppIPaddress;
	}

	public void setNetAppIPaddress(String netAppIPaddress) {
		this.netAppIPaddress = netAppIPaddress;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
