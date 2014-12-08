package com.sobey.sdn.webservice;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.sdn.constans.WsConstants;
import com.sobey.sdn.service.SDNService;

@WebService(serviceName = "SDNSoapService", endpointInterface = "com.sobey.sdn.webservice.SDNSoapService", targetNamespace = WsConstants.NS)
public class SDNSoapServiceImpl implements SDNSoapService {

	@Autowired
	private SDNService service;
}
