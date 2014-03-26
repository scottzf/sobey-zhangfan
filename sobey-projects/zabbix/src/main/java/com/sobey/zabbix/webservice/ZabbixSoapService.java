package com.sobey.zabbix.webservice;

import javax.jws.WebService;

import com.sobey.zabbix.constans.WsConstants;

/**
 * Zabbix对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "ZabbixSoapService", targetNamespace = WsConstants.NS)
public interface ZabbixSoapService {

}
