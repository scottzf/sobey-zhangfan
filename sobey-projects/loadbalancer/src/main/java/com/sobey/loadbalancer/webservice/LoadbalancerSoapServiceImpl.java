package com.sobey.loadbalancer.webservice;

import javax.jws.WebParam;

import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.loadbalancer.service.NitroService;
import com.sobey.loadbalancer.webservice.response.dto.ELBParameter;
import com.sobey.loadbalancer.webservice.response.result.WSResult;

public class LoadbalancerSoapServiceImpl implements LoadbalancerSoapService {

	@Autowired
	public NitroService nitroService;

	@Override
	public WSResult createELBByLoadbalancer(@WebParam(name = "ELBParameter") ELBParameter parameter) {

		WSResult result = new WSResult();

		boolean falg = nitroService.createElb(parameter);

		if (!falg) {
			result.setCode(WSResult.PARAMETER_ERROR);
		}

		return result;

	}

	@Override
	public WSResult deleteELBByLoadbalancer(@WebParam(name = "ELBParameter") ELBParameter parameter) {

		WSResult result = new WSResult();

		boolean falg = nitroService.deleteElb(parameter);

		if (!falg) {
			result.setCode(WSResult.PARAMETER_ERROR);
		}

		return result;
	}

}
