package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ServiceBasicDTO;

@XmlRootElement(name = "ElbDTO")
@XmlType(name = "ElbDTO", namespace = WsConstants.NS)
public class ElbDTO extends ServiceBasicDTO {

	private Integer ipaddress;
	private Integer subnet;

	public Integer getIpaddress() {
		return ipaddress;
	}

	public Integer getSubnet() {
		return subnet;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setSubnet(Integer subnet) {
		this.subnet = subnet;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}