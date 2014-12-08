package com.sobey.loadbalancer.webservice.response.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.loadbalancer.constans.WsConstants;

@XmlRootElement(name = "ElbSync")
@XmlType(name = "ElbSync", namespace = WsConstants.NS)
public class ElbSync {

	private String vip;
	private List<ElbPolicySync> policySyncs;

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public List<ElbPolicySync> getPolicySyncs() {
		return policySyncs;
	}

	public void setPolicySyncs(List<ElbPolicySync> policySyncs) {
		this.policySyncs = policySyncs;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}