package com.sobey.api.webservice;

import javax.jws.WebService;

import com.sobey.api.constans.WsConstants;

/**
 * 公共功能木块对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "ApiSoapService", targetNamespace = WsConstants.NS)
public interface ApiSoapService {

}
