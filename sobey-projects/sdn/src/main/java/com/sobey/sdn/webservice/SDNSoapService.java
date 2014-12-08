package com.sobey.sdn.webservice;

import javax.jws.WebService;

import com.sobey.sdn.constans.WsConstants;

/**
 * SDN对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "SDNSoapService", targetNamespace = WsConstants.NS)
public interface SDNSoapService {

}
