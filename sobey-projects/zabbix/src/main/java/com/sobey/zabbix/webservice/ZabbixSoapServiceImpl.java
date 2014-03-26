package com.sobey.zabbix.webservice;

import javax.jws.WebService;

import com.sobey.zabbix.constans.WsConstants;

@WebService(serviceName = "ZabbixSoapService", endpointInterface = "com.sobey.zabbix.webservice.ZabbixSoapService", targetNamespace = WsConstants.NS)
public class ZabbixSoapServiceImpl implements ZabbixSoapService {

}
