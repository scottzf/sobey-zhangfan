package com.sobey.switches.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.switches.constans.WsConstants;

/**
 * 创建ESG时的所需Rule对象
 * 
 * <pre>
 * source			源IP/网段
 * sourceNetMask		源子网掩码
 * destination		目标IP/网段
 * destinationNetMask	目标子网掩码
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement
@XmlType(name = "RuleParameter", namespace = WsConstants.NS)
public class RuleParameter {

	/**
	 * 源IP/网段
	 */
	private String source;

	/**
	 * 源子网掩码
	 */
	private String sourceNetMask;

	/**
	 * 目标IP/网段
	 */
	private String destination;

	/**
	 * 目标子网掩码
	 */
	private String destinationNetMask;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceNetMask() {
		return sourceNetMask;
	}

	public void setSourceNetMask(String sourceNetMask) {
		this.sourceNetMask = sourceNetMask;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestinationNetMask() {
		return destinationNetMask;
	}

	public void setDestinationNetMask(String destinationNetMask) {
		this.destinationNetMask = destinationNetMask;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
