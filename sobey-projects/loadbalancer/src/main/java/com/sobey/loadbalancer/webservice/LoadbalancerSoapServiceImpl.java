package com.sobey.loadbalancer.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.loadbalancer.constans.WsConstants;
import com.sobey.loadbalancer.service.LoadbalanceService;
import com.sobey.loadbalancer.webservice.response.dto.ELBParameter;
import com.sobey.loadbalancer.webservice.response.result.WSResult;

@WebService(serviceName = "LoadbalancerSoapService", endpointInterface = "com.sobey.loadbalancer.webservice.LoadbalancerSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class LoadbalancerSoapServiceImpl implements LoadbalancerSoapService {

	@Autowired
	public LoadbalanceService service;

	@Override
	public WSResult createELBByLoadbalancer(@WebParam(name = "elbParameter") ELBParameter elbParameter) {

		WSResult result = new WSResult();

		boolean falg = service.createElb(elbParameter);

		if (!falg) {
			result.setError(WSResult.SYSTEM_ERROR, "Elb创建失败");
		}

		return result;

	}

	@Override
	public WSResult deleteELBByLoadbalancer(@WebParam(name = "elbParameter") ELBParameter elbParameter) {

		WSResult result = new WSResult();

		boolean falg = service.deleteElb(elbParameter);

		if (!falg) {
			result.setError(WSResult.SYSTEM_ERROR, "Elb删除失败");
		}

		return result;
	}

}
