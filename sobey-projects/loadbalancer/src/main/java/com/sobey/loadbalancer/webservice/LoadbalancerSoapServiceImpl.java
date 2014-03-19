package com.sobey.loadbalancer.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.loadbalancer.constans.WsConstants;
import com.sobey.loadbalancer.service.NitroService;
import com.sobey.loadbalancer.webservice.response.dto.ELBParameter;
import com.sobey.loadbalancer.webservice.response.result.WSResult;

@WebService(serviceName = "LoadbalancerSoapService", endpointInterface = "com.sobey.loadbalancer.webservice.LoadbalancerSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class LoadbalancerSoapServiceImpl implements LoadbalancerSoapService {

	@Autowired
	public NitroService service;

	@Override
	public WSResult createELBByLoadbalancer(@WebParam(name = "ELBParameter") ELBParameter parameter) {

		WSResult result = new WSResult();

		boolean falg = service.createElb(parameter);

		if (!falg) {
			result.setCode(WSResult.SYSTEM_ERROR);
		}

		return result;

	}

	@Override
	public WSResult deleteELBByLoadbalancer(@WebParam(name = "ELBParameter") ELBParameter parameter) {

		WSResult result = new WSResult();

		boolean falg = service.deleteElb(parameter);

		if (!falg) {
			result.setCode(WSResult.SYSTEM_ERROR);
		}

		return result;
	}

}
