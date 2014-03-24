package com.sobey.nagios.webservice;

import javax.jws.WebService;

import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.nagios.constans.WsConstants;
import com.sobey.nagios.service.NagiosService;
import com.sobey.nagios.webservice.response.dto.NagiosPingDTO;

@WebService(serviceName = "NagiosSoapService", endpointInterface = "com.sobey.nagios.webservice.NagiosSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class NagiosSoapServiceImpl implements NagiosSoapService {

	@Autowired
	private NagiosService service;

	@Override
	public NagiosPingDTO getNagiosPing(String itemId, String ipaddress, String startDate, String endDate) {
		return service.ping(itemId, ipaddress, startDate, endDate);
	}

}
