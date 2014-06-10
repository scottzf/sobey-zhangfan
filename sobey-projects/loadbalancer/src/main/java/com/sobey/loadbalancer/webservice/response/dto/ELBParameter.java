package com.sobey.loadbalancer.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.loadbalancer.constans.WsConstants;

/**
 * Loadbalancer上,执行脚本所需参数的ELBParameter对象:
 * 
 * <pre>
 * vip		虚拟IP
 * publicIPs	公网IP集合
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "ELBParameter")
@XmlType(name = "ELBParameter", namespace = WsConstants.NS)
public class ELBParameter {

	private String vip;

	/**
	 * 公网IP集合
	 */
	private ArrayList<ELBPublicIPParameter> publicIPs;

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public ArrayList<ELBPublicIPParameter> getPublicIPs() {
		return publicIPs;
	}

	public void setPublicIPs(ArrayList<ELBPublicIPParameter> publicIPs) {
		this.publicIPs = publicIPs;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
