package com.sobey.instance.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.instance.constans.WsConstants;
import com.sobey.instance.service.VMService;
import com.sobey.instance.webservice.response.dto.CloneVMParameter;
import com.sobey.instance.webservice.response.dto.DestroyVMParameter;
import com.sobey.instance.webservice.response.dto.PowerVMParameter;
import com.sobey.instance.webservice.response.dto.ReconfigVMParameter;
import com.sobey.instance.webservice.response.dto.RelationVMParameter;
import com.sobey.instance.webservice.response.result.WSResult;

@WebService(serviceName = "InstanceSoapService", endpointInterface = "com.sobey.instance.webservice.InstanceSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class InstanceSoapServiceImpl implements InstanceSoapService {

	@Autowired
	public VMService service;

	@Override
	public WSResult cloneVMByInstance(@WebParam(name = "cloneVMParameter") CloneVMParameter cloneVMParameter) {

		WSResult result = new WSResult();

		boolean falg = service.cloneVM(cloneVMParameter);

		if (!falg) {
			result.setCode(WSResult.SYSTEM_ERROR);
		}

		return result;
	}

	@Override
	public WSResult destroyVMByInstance(@WebParam(name = "destroyVMParameter") DestroyVMParameter destroyVMParameter) {

		WSResult result = new WSResult();

		boolean falg = service.destroyVM(destroyVMParameter);

		if (!falg) {
			result.setCode(WSResult.SYSTEM_ERROR);
		}

		return result;
	}

	@Override
	public WSResult reconfigVMByInstance(@WebParam(name = "reconfigVMParameter") ReconfigVMParameter reconfigVMParameter) {

		WSResult result = new WSResult();

		boolean falg = service.reconfigVM(reconfigVMParameter);

		if (!falg) {
			result.setCode(WSResult.SYSTEM_ERROR);
		}

		return result;
	}

	@Override
	public WSResult powerVMByInstance(@WebParam(name = "powerVMParameter") PowerVMParameter powerVMParameter) {

		WSResult result = new WSResult();

		boolean falg = service.powerVM(powerVMParameter);

		if (!falg) {
			result.setCode(WSResult.SYSTEM_ERROR);
		}

		return result;
	}

	@Override
	public RelationVMParameter getVMAndHostRelationByInstance() {
		return service.getVMAndHostRelation();
	}

}
