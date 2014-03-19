package com.sobey.api.webservice;

import javax.jws.WebService;

import com.sobey.api.constans.WsConstants;

@WebService(serviceName = "ApiSoapService", endpointInterface = "com.sobey.api.webservice.ApiSoapService", targetNamespace = WsConstants.NS)
public class ApiSoapServiceImpl implements ApiSoapService {

}
