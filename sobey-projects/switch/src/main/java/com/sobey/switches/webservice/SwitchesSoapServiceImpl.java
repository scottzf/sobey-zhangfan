package com.sobey.switches.webservice;

import javax.jws.WebService;

import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.switches.constans.WsConstants;
import com.sobey.switches.service.SwitchService;
import com.sobey.switches.webservice.response.dto.SwitchPolicyParameter;
import com.sobey.switches.webservice.response.result.WSResult;

@WebService(serviceName = "SwitchesSoapService", endpointInterface = "com.sobey.switches.webservice.SwitchesSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class SwitchesSoapServiceImpl implements SwitchesSoapService {

	@Autowired
	private SwitchService service;

	@Override
	public WSResult createSinglePolicyBySwitch(SwitchPolicyParameter switchPolicyParameter) {
		return service.createSinglePolicy(switchPolicyParameter);
	}

	@Override
	public WSResult createMultiplePolicyBySwitch(SwitchPolicyParameter switchPolicyParameter) {
		return service.createMultiplePolicy(switchPolicyParameter);
	}

}
