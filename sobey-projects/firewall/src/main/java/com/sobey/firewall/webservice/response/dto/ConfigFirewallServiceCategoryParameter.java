package com.sobey.firewall.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.firewall.constans.WsConstants;

@XmlRootElement(name = "ConfigFirewallServiceCategoryParameter")
@XmlType(name = "ConfigFirewallServiceCategoryParameter", namespace = WsConstants.NS)
public class ConfigFirewallServiceCategoryParameter {

	/**
	 * 行为 allow & deny
	 */
	private String action;

	/**
	 * 目标地址,对外的情况填写"ALL"
	 */
	private String categoryName;

	/**
	 * 目标地址,对外的情况填写"ALL"
	 */
	private String dstaddr;

	/**
	 * 目标接口
	 */
	private String dstintf;

	/**
	 * 防火墙(路由)登录密码
	 */
	private String password;

	/**
	 * 防火墙策略ID,唯一递增
	 */
	private Integer policyId;

	/**
	 * 端口范围
	 */
	private String portrange;

	/**
	 * 协议 TCP & UDP
	 */
	private String protocol;

	/**
	 * 源地址
	 */
	private String srcaddr;

	/**
	 * 源接口
	 */
	private String srcintf;

	/**
	 * 防火墙(路由)IP地址
	 */
	private String url;

	/**
	 * 防火墙(路由)登录名
	 */
	private String userName;

	public String getAction() {
		return action;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getDstaddr() {
		return dstaddr;
	}

	public String getDstintf() {
		return dstintf;
	}

	public String getPassword() {
		return password;
	}

	public Integer getPolicyId() {
		return policyId;
	}

	public String getPortrange() {
		return portrange;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getSrcaddr() {
		return srcaddr;
	}

	public String getSrcintf() {
		return srcintf;
	}

	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setDstaddr(String dstaddr) {
		this.dstaddr = dstaddr;
	}

	public void setDstintf(String dstintf) {
		this.dstintf = dstintf;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}

	public void setPortrange(String portrange) {
		this.portrange = portrange;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setSrcaddr(String srcaddr) {
		this.srcaddr = srcaddr;
	}

	public void setSrcintf(String srcintf) {
		this.srcintf = srcintf;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
