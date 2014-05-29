package com.sobey.zabbix.service;

import java.io.IOException;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.zabbix.dao.ZabbixApiDao;
import com.sobey.zabbix.webservice.response.dto.ZItemDTO;

@Service
public class ZabbixApiService {

	@Autowired
	private ZabbixApiDao dao;

	private static Logger logger = LoggerFactory.getLogger(ZabbixApiService.class);

	public void deleteHost(String hostName) {
		try {
			dao.deleteHost(dao.getHostId(hostName));
		} catch (JSONException | IOException e) {
			logger.info("Exception::deleteHost::message=" + e);
		}
	}

	public ZItemDTO getItem(String hostName, String itemKey) {

		ZItemDTO dto = new ZItemDTO();

		try {

			dto = dao.getItem(dao.getHostId(hostName), itemKey);

		} catch (JSONException | IOException e) {

			logger.info("Exception::getItem::message=" + e);

		}

		return dto;
	}

}
