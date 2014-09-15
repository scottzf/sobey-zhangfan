package com.sobey.zabbix.entity;

/**
 * 定义 zabbix api Item
 * 
 * @author Administrator
 *
 */
public class ZItem {

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

}
