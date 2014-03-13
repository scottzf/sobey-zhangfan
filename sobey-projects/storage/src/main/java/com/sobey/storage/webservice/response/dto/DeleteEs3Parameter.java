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
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "DeleteEs3Parameter")
@XmlType(name = "DeleteEs3Parameter", namespace = WsConstants.NS)
public class DeleteEs3Parameter {

	/**
	 * 卷名
	 */
	private String volumeName;

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
