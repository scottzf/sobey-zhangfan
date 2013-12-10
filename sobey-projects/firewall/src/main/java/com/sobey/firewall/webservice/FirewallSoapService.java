package com.sobey.firewall.webservice;

import javax.jws.WebService;

import com.sobey.firewall.constans.WsConstants;

/**
 * 防火墙firewall对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "FirewallSoapService", targetNamespace = WsConstants.NS)
public interface FirewallSoapService {

}
