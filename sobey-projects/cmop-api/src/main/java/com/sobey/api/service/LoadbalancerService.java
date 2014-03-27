package com.sobey.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.generate.loadbalancer.ELBParameter;
import com.sobey.generate.loadbalancer.LoadbalancerSoapService;
import com.sobey.generate.loadbalancer.WSResult;

@Service
public class LoadbalancerService {

	@Autowired
	private LoadbalancerSoapService service;

	public WSResult createElb(ELBParameter elbParameter) {
		return service.createELBByLoadbalancer(elbParameter);
	}

	public WSResult deleteElb(ELBParameter elbParameter) {
		return service.deleteELBByLoadbalancer(elbParameter);
	}
}
