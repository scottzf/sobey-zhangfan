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
	 * 更新时间
	 */
	private String clock;

	/**
	 * 监控项ID
	 */
	private String itemid;

	/**
	 * 单位
	 */
	private String units;

	/**
	 * 值
	 */
	private String value;

	/**
	 *
	 * History object types to return. or Type of information of the item.<br>
	 *
	 * 注意返回数据的格式不同,接口响应速度也不同,最大可以相差6倍.所以要严格的遵循返回格式.<br>
	 * 
	 * 查询history时,history是以item中查询的value_type为准
	 *
	 * <pre>
	 * 0 - float;
	 * 1 - string;
	 * 2 - log;
	 * 3 - integer;
	 * 4 - text.
	 * </pre>
	 */
	private Integer valueType;

	public String getClock() {
		return clock;
	}

	public String getItemid() {
		return itemid;
	}

	public String getUnits() {
		return units;
	}

	public String getValue() {
		return value;
	}

	public Integer getValueType() {
		return valueType;
	}

	public void setClock(String clock) {
		this.clock = clock;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
