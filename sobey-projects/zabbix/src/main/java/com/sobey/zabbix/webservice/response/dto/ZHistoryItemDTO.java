package com.sobey.zabbix.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.zabbix.constans.WsConstants;

/**
 * 定义 zabbix api History
 * 
 * @author Administrator
 *
 */
@XmlRootElement(name = "ZHistoryItemDTO")
@XmlType(name = "ZHistoryItemDTO", namespace = WsConstants.NS)
public class ZHistoryItemDTO {

	private ArrayList<ZItemDTO> zItemDTOs;

	public ArrayList<ZItemDTO> getzItemDTOs() {
		return zItemDTOs;
	}

	public void setzItemDTOs(ArrayList<ZItemDTO> zItemDTOs) {
		this.zItemDTOs = zItemDTOs;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
