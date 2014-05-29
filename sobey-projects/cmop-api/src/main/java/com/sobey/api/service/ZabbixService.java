package com.sobey.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.generate.zabbix.ZItemDTO;
import com.sobey.generate.zabbix.ZabbixSoapService;

@Service
public class ZabbixService {

	@Autowired
	private ZabbixSoapService service;

	public void deleteHost(String name) {
		service.deleleHost(name);
	}

	public ZItemDTO getItem(String name, String itemKey) {
		return service.getZItem(name, itemKey);
	}

}
