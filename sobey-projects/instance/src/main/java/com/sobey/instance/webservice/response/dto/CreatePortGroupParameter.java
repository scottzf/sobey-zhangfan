package com.sobey.instance.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

/**
 * 创建端口组的参数对象
 * 
 * @author Administrator
 *
 */
@XmlRootElement(name = "CreatePortGroupParameter")
@XmlType(name = "CreatePortGroupParameter", namespace = WsConstants.NS)
public class CreatePortGroupParameter {

	/**
	 * 数据中心
	 */
	private String datacenter;

	/**
	 * 宿主机名称
	 */
	private String hostName;

	/**
	 * 端口组名称
	 */
	private String portGroupName;

	/**
	 * 标准交换机名称
	 */
	private String virtualSwitchName;

	/**
	 * vlanId. 建议采用12--4093之间的数字
	 */
	private Integer vlanId;

	public String getDatacenter() {
		return datacenter;
	}

	public String getHostName() {
		return hostName;
	}

	public String getPortGroupName() {
		return portGroupName;
	}

	public String getVirtualSwitchName() {
		return virtualSwitchName;
	}

	public Integer getVlanId() {
		return vlanId;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setPortGroupName(String portGroupName) {
		this.portGroupName = portGroupName;
	}

	public void setVirtualSwitchName(String virtualSwitchName) {
		this.virtualSwitchName = virtualSwitchName;
	}

	public void setVlanId(Integer vlanId) {
		this.vlanId = vlanId;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
