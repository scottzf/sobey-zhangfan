package com.sobey.zabbix.entity;

import java.util.List;

/**
 * 定义 zabbix api History<br>
 * 
 * 具体参数可查看 https://www.zabbix.com/documentation/2.2/manual/api/reference/history/object
 * 
 * @author Administrator
 *
 */
public class ZHistoryItem {

	private List<ZItem> zItems;

	public List<ZItem> getzItems() {
		return zItems;
	}

	public void setzItems(List<ZItem> zItems) {
		this.zItems = zItems;
	}

}
