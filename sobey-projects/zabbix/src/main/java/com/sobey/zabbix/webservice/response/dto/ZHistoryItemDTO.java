package com.sobey.zabbix.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.zabbix.constans.WsConstants;
import com.sobey.zabbix.entity.ZHistoryItem;

/**
 * 定义 zabbix api History
 * 
 * @author Administrator
 *
 */
@XmlRootElement(name = "ZHistoryItemDTO")
@XmlType(name = "ZHistoryItemDTO", namespace = WsConstants.NS)
public class ZHistoryItemDTO {

	private ArrayList<ZHistoryItem> zHistoryItems;

	public ArrayList<ZHistoryItem> getzHistoryItems() {
		return zHistoryItems;
	}

	public void setzHistoryItems(ArrayList<ZHistoryItem> zHistoryItems) {
		this.zHistoryItems = zHistoryItems;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
