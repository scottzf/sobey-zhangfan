package com.sobey.zabbix.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.zabbix.constans.WsConstants;

/**
 * 定义 zabbix api Item
 * 
 * @author Administrator
 *
 */
@XmlRootElement(name = "ZItemDTO")
@XmlType(name = "ZItemDTO", namespace = WsConstants.NS)
public class ZItemDTO {

	/**
	 * 监控项ID
	 */
	private String itemid;

	/**
	 * 更新时间
	 */
	private String clock;

	/**
	 * 值
	 */
	private String value;

	/**
	 * 单位
	 */
	private String units;

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getClock() {
		return clock;
	}

	public void setClock(String clock) {
		this.clock = clock;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
