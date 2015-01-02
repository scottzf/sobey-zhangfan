package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

/**
 * 创建标准交换机的参数对象
 * 
 * @author Administrator
 *
 */
@XmlRootElement(name = "CreateStandardSwitchParameter")
@XmlType(name = "CreateStandardSwitchParameter", namespace = WsConstants.NS)
public class CreateStandardSwitchParameter {

	/**
	 * 数据中心
	 */
	private String datacenter;

	/**
	 * 宿主机名称
	 */
	private String hostName;

	/**
	 * 标准交换机名称
	 */
	private String virtualSwitchName;

	public String getDatacenter() {
		return datacenter;
	}

	public String getHostName() {
		return hostName;
	}

	public String getVirtualSwitchName() {
		return virtualSwitchName;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setVirtualSwitchName(String virtualSwitchName) {
		this.virtualSwitchName = virtualSwitchName;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
