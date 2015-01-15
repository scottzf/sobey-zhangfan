package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.ServiceBasicDTO;

@XmlRootElement(name = "VpnDTO")
@XmlType(name = "VpnDTO", namespace = WsConstants.NS)
public class VpnDTO extends ServiceBasicDTO {

	private String password;
	private Integer tenants;
	private String username;

	public String getPassword() {
		return password;
	}

	public Integer getTenants() {
		return tenants;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}