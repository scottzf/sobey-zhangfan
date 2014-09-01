package com.sobey.zabbix.entity;

/**
 * 定义 zabbix api History<br>
 * 
 * 具体参数可查看 https://www.zabbix.com/documentation/2.2/manual/api/reference/history/object
 * 
 * @author Administrator
 *
 */
public class ZHistoryItem {

	private String itemid;
	private String clock;
	private String value;
	private String ns;

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

	public String getNs() {
		return ns;
	}

	public void setNs(String ns) {
		this.ns = ns;
	}

}
