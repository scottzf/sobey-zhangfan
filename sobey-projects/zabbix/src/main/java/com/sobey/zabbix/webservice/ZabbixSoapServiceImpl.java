package com.sobey.zabbix.webservice;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.zabbix.constans.WsConstants;
import com.sobey.zabbix.service.ZabbixApiService;
import com.sobey.zabbix.webservice.response.dto.ZItemDTO;

@WebService(serviceName = "ZabbixSoapService", endpointInterface = "com.sobey.zabbix.webservice.ZabbixSoapService", targetNamespace = WsConstants.NS)
public class ZabbixSoapServiceImpl implements ZabbixSoapService {

	@Autowired
	private ZabbixApiService service;

	@Override
	public ZItemDTO getZItem(String name, String itemkey) {

		return service.getItem(name, itemkey);
	}

	@Override
	public void deleleHost(String name) {
		service.deleteHost(name);
	}

}
