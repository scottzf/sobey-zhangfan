package com.sobey.storage.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.storage.constans.WsConstants;

/**
 * netapp上,执行脚本所需参数的对象:
 * 
 * <pre>
 * clientIPaddress		客户端IP
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "UmountEs3Parameter")
@XmlType(name = "UmountEs3Parameter", namespace = WsConstants.NS)
public class UmountEs3Parameter {

	/**
	 * 客户端IP
	 */
	private String clientIPaddress;

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
