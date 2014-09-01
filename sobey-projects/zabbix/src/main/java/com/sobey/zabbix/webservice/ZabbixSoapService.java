package com.sobey.zabbix.webservice;

import javax.jws.WebService;

import com.sobey.zabbix.constans.WsConstants;
import com.sobey.zabbix.webservice.response.dto.ZHistoryItemDTO;
import com.sobey.zabbix.webservice.response.dto.ZItemDTO;

/**
 * Zabbix对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "ZabbixSoapService", targetNamespace = WsConstants.NS)
public interface ZabbixSoapService {

	ZItemDTO getZItem(String name, String itemkey);

	void deleleHost(String name);

	ZHistoryItemDTO getZHistoryItem(String name, String itemkey);
}
