package com.sobey.dns.webservice;

import javax.jws.WebService;

import com.sobey.dns.constans.WsConstants;

/**
 * DNS对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "DnsSoapService", targetNamespace = WsConstants.NS)
public interface DnsSoapService {

}
