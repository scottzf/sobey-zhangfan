package com.sobey.loadbalancer.webservice;

import javax.jws.WebService;

import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.loadbalancer.constans.WsConstants;
import com.sobey.loadbalancer.service.LoadbalanceService;
import com.sobey.loadbalancer.webservice.response.dto.ELBParameter;
import com.sobey.loadbalancer.webservice.response.dto.ElbSync;
import com.sobey.loadbalancer.webservice.response.result.DTOListResult;
import com.sobey.loadbalancer.webservice.response.result.WSResult;

@WebService(serviceName = "LoadbalancerSoapService", endpointInterface = "com.sobey.loadbalancer.webservice.LoadbalancerSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class LoadbalancerSoapServiceImpl implements LoadbalancerSoapService {

	@Autowired
	public LoadbalanceService service;

	@Override
	public WSResult createELBByLoadbalancer(ELBParameter elbParameter) {
		return service.createElb(elbParameter);
	}

	@Override
	public WSResult deleteELBByLoadbalancer(ELBParameter elbParameter) {
		return service.deleteElb(elbParameter);
	}

	@Override
	public DTOListResult<ElbSync> getELBConfig(ELBParameter elbParameter) {
		return service.getElbSyncList(elbParameter);
	}

}
