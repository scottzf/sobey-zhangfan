package com.sobey.dns.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.dns.constans.WsConstants;

/**
 * DNS上,执行脚本所需参数的DNSParameter对象:
 * 
 * <pre>
 * domianName	域名
 * domianType	域名类型: GSLB,A,CNAME
 * publicIPs	公网IP集合
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "DNSParameter")
@XmlType(name = "DNSParameter", namespace = WsConstants.NS)
public class DNSParameter {

	/**
	 * 域名
	 */
	private String domianName;

	/**
	 * 域名类型: GSLB,A,CNAME
	 */
	private String domianType;

	/**
	 * 公网IP集合
	 */
	private ArrayList<DNSPublicIPParameter> publicIPs;

	public String getDomianName() {
		return domianName;
	}

	public void setDomianName(String domianName) {
		this.domianName = domianName;
	}

	public String getDomianType() {
		return domianType;
	}

	public void setDomianType(String domianType) {
		this.domianType = domianType;
	}

	public ArrayList<DNSPublicIPParameter> getPublicIPs() {
		return publicIPs;
	}

	public void setPublicIPs(ArrayList<DNSPublicIPParameter> publicIPs) {
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
