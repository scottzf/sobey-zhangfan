package com.sobey.firewall.webservice;

import javax.jws.WebService;

import com.sobey.firewall.constans.WsConstants;

@WebService(serviceName = "FirewallSoapService", endpointInterface = "com.sobey.firewall.webservice.FirewallSoapService", targetNamespace = WsConstants.NS)
public class FirewallSoapServiceImpl implements FirewallSoapService {

}
