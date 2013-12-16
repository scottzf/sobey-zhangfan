package com.sobey.dns.webservice;

import javax.jws.WebService;

import com.sobey.dns.constans.WsConstants;

@WebService(serviceName = "DnsSoapService", endpointInterface = "com.sobey.dns.webservice.DnsSoapService", targetNamespace = WsConstants.NS)
public class DnsSoapServiceImpl implements DnsSoapService {

}
